import {buildTraceabilityMatrixTable, listOfColumns} from "./traceability-utils";

function escapeHtml(text) {
    return text && text
         .replace(/&/g, "&amp;")
         .replace(/</g, "&lt;")
         .replace(/>/g, "&gt;")
         .replace(/"/g, "&quot;")
         .replace(/'/g, "&#039;")
        || "";
}

function renderCell(cell) {
    if (cell.subsequentRow) {
        return "";
    }
    switch (cell.type) {
        case "requirement": {
            if (cell.requirement) {
                return `<a href="/requirements/${escapeHtml(cell.requirement.key)}">${escapeHtml(cell.requirement.key)}</a>`;
            }
            return "";
        }
        case "user": {
            if (cell.value) {
                return escapeHtml(cell.value);
            }
            return "";
        }
        case "text": return escapeHtml(cell.value);
        default: return "Type unknown: " + escapeHtml(cell.type);
    }
}

fetch("/rest/requirements")
    .then(async result => {
        let requirements = await result.json();
        let table = buildTraceabilityMatrixTable(requirements, listOfColumns);
        let html = "<table class='aui' style='width: auto'><thead><tr>";
        listOfColumns.forEach(column => html += "<th>" + escapeHtml(column) + "</th>");
        html += "</tr></thead>";
        html += "<tbody>";
        table.forEach(row => {
            html += "<tr>";
            row.forEach(cell => {
                html += cell.subsequentRow ? "<td class='subsequent-row'>" : "<td>";
                html += renderCell(cell);
                html += "</td>";
            });
            html += "</tr>";
        });
        html += "</tbody></table>";
        document.getElementById("root").innerHTML = html;
    });
