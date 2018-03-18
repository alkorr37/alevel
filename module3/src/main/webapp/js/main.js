const popup = document.querySelector(".add-table-popup");
const actions = document.querySelector("div.actions");
const contents = document.querySelector(".table-wrapp");

let appData = {
    tables: [],
    contents: [],
    selectedTableId: null
};

function setupBaseListeners() {
    actions.querySelector("a[title='Add Table']").addEventListener("click", () => popup.style.display = "flex");
    actions.querySelector("a[title='Remove Table']").addEventListener("click", () => removeTable());
    actions.querySelector("a[title='Add Row']").addEventListener("click", () => addRow());
    actions.querySelector("a[title='Add Col']").addEventListener("click", () => addColumn());

    popup.querySelector("a[title='Cancel']").addEventListener("click", () => popup.style.display = "none");
    popup.querySelector(".add-table-popup input[type='Submit']").addEventListener("click", () => {
        addTable();
        popup.querySelectorAll("input[id]").forEach(elem => elem.value = "");
        popup.style.display = "none";
    });
}

function getCurTable() {
    const {tables, selectedTableId} = appData;
    return tables.find(table => table.id === selectedTableId);
}

function updateModel() {
    axios.get("http://localhost:8080/table").then(({data}) => {
        appData.tables = data;
        syncNavPanel();
        syncActions();
        syncContents();
    });
}

function getContents() {
    axios.get(`http://localhost:8080/table/${appData.selectedTableId}`).then(({data}) => {
        appData.contents = data;
        syncContents();
        syncActions();
    });
}

function syncNavPanel() {
    const navElem = document.querySelector(".main-nav > ul");
    navElem.innerHTML = "";
    appData.tables.forEach(table => {
        const li = document.createElement("li");
        li.innerHTML = `<a href="#" title="${table.name}"><span>${table.name}</span></a>`;
        if (table.id === appData.selectedTableId) {
            li.classList.add("selected");
        }
        navElem.appendChild(li);

        li.addEventListener("click", function () {
            document.querySelectorAll(".main-nav li").forEach(elem => elem.classList.remove("selected"));
            this.classList.add("selected");
            appData.selectedTableId = table.id;
            getContents();
        });
    })
}

function syncActions() {
    actions.querySelectorAll("a:not([title='Add Table'])").forEach(elem =>
        elem.classList[appData.selectedTableId !== null ? "remove" : "add"]("disabled"));
}

function syncContents() {
    function getHeader() {
        const header = document.createElement("tr");
        for (let i = 0; i < curTable.colCount; i++) {
            const th = document.createElement("th");
            th.innerHTML = `<a href="#" title="remove" class="remove-btn"><i class="fas fa-trash-alt"></i></a>`;
            header.appendChild(th);
        }
        const th = document.createElement("th");
        th.classList.add("remove-col");
        th.innerHTML = "Remove";
        header.appendChild(th);
        return header;
    }

    function injectBody() {
        appData.contents.forEach((row, i) => {
            const tr = document.createElement("tr");
            row.forEach((cell, j) => {
                const td = document.createElement("td");

                const input = document.createElement("input");
                input.style.display = "none";
                input.value = cell || "";
                input.type = "text";
                input.addEventListener("blur", updateCell.bind(this, i, j));

                const span = document.createElement("span");
                span.innerHTML += cell || "";

                td.appendChild(input);
                td.appendChild(span);

                tr.appendChild(td);
            });
            const remove = document.createElement("td");
            remove.innerHTML = "<a href=\"#\" title=\"remove\" class=\"remove-btn\"><i class=\"fas fa-trash-alt\"></i></a>";
            tr.appendChild(remove);
            table.appendChild(tr);
        });
    }

    function getSaveBtn() {
        const div = document.createElement("div");
        div.classList.add("table-actions");
        div.innerHTML = `<a href="#" title="Save" class="lnk-btn"><span>Save Table</span></a>`;
        return div;
    }

    function setContentListeners() {
        contents.querySelectorAll("th a.remove-btn").forEach((a, i) => a.addEventListener("click", removeColumn.bind(this, i)));
        contents.querySelectorAll("td a.remove-btn").forEach((a, i) => a.addEventListener("click", removeRow.bind(this, i)));

        contents.querySelectorAll("tr td:not(:last-child)").forEach(td => td.addEventListener("dblclick", function () {
            const input = this.querySelector("input");
            const span = this.querySelector("span");
            const isInputHidden = input.style.display === "none";
            (isInputHidden ? input : span).style.display = "flex";
            (isInputHidden ? span : input).style.display = "flex";
            if (isInputHidden) {
                input.focus();
            }
        }));

        contents.querySelector("a[title='Save']").addEventListener("click", () => saveContents());
    }

    const curTable = getCurTable();
    contents.innerHTML = "";

    if (!curTable) {
        return;
    }

    const table = document.createElement("table");
    table.appendChild(getHeader());
    injectBody();

    contents.appendChild(table);
    contents.appendChild(getSaveBtn());
    setContentListeners();
}

function addRow() {
    const curTable = getCurTable();
    if (!curTable) {
        return;
    }

    curTable.rowCount++;
    appData.contents.push(Array(curTable.colCount).fill(null));
    syncContents();
}

function addColumn() {
    const curTable = getCurTable();
    if (!curTable) {
        return;
    }

    curTable.colCount++;
    appData.contents.forEach(row => row.push(null));
    syncContents();
}

function removeRow(index) {
    const curTable = getCurTable();
    if (!curTable) {
        return;
    }

    curTable.rowCount--;
    appData.contents.splice(index, 1);
    syncContents();
}

function removeColumn(index) {
    const curTable = getCurTable();
    if (!curTable) {
        return;
    }

    appData.contents.forEach(row => row.splice(index, 1));
    curTable.colCount--;
    syncContents();
}

function updateCell(row, column, event) {
    appData.contents[row][column] = event.target.value;
    syncContents();
}

function addTable() {
    axios.post("http://localhost:8080/table", {
        name: popup.querySelector("#tableName").value,
        rowCount: popup.querySelector("#rowsNumb").value,
        colCount: popup.querySelector("#collsNumb").value
    }).then(({data}) => {
        appData.selectedTableId = data;
        updateModel();
        getContents();
    });
}

function removeTable() {
    if (!getCurTable()) {
        return;
    }

    axios.delete(`http://localhost:8080/table/${appData.selectedTableId}`)
        .then(() => {
            appData.selectedTableId = null;
            updateModel();
        });
}

function saveContents() {
    const curTable = getCurTable();
    if (!curTable) {
        return;
    }

    axios.post(`http://localhost:8080/table/${appData.selectedTableId}`, appData.contents)
        .then(updateModel);
}

setupBaseListeners();

updateModel();