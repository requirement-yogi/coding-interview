////////////////////
//
//
// This class displays a traceability matrix by ... concatenating a big string of HTML. It works! And it's faster!
//
//
////////////////////////


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

/**
 * The GREAT thing about this traceability report, is that you can add columns!
 * @type {string[]}
 */
export const listOfColumns = [
    "requirement",
    "@type",
    "@author",
    "dependencies", // This displays the requirements attached to the parent requirements.
    "@author", // The property of the second requirements
    "dependencies",
    "@author",
    "@cost"/*,
    "dependencies",
    "@cost"*/
];

/**
 * Builds a big array of 2 dimensions containing all the cells that should be displayed,
 * - The first column is the list of requirements,
 * - Every subsequent column is build dynamically depending on what is requested in the 'columns' array.
 *
 * @param requirements a list of requirements, with a "dependencies" property
 * @param columns the list of columns that you want
 */
export function buildTraceabilityMatrixTable(requirements, columns) {
    // The structure is table[row][column] = { type : "type of the cell", ...values }
    let table =
        // Column 1 contains a list of "requirement" cells
        requirements.map(requirement => [ // It's an array containing the list of cells for this row.
            {
                type: "requirement",
                requirement: requirement
            }
        ]);

    /** In a table[column][row] structure, adds a row to each column, containing the same contents as
     * the rowId.
     */
    function copyRow(table, rowId, insertAtRowId) {
        let rowToCopy = [...table[rowId]];
        // All cells were copied by reference, but we want a copy by value
        rowToCopy = rowToCopy.map(cell => Object.assign({}, cell));
        table.splice(insertAtRowId, 0, rowToCopy);
        table[insertAtRowId].forEach(cell => cell.subsequentRow = true);
    }

    for (let columnNumber = 1; columnNumber < columns.length; columnNumber++) {
        let columnName = columns[columnNumber];
        for (let rowNumber = 0; rowNumber < table.length;) {
            let parentCell = table[rowNumber][columnNumber - 1];
            if (!parentCell) debugger;
            let children = getChildren(parentCell.requirement, columnName);
            if (!children) debugger;
            if (children.length === 0) {
                table[rowNumber][columnNumber] = { type: "empty", requirement: parentCell.requirement };
                rowNumber++;
            } else {
                for (let childIndex = 0; childIndex < children.length; childIndex++) {
                    let child = children[childIndex];
                    if (!child.requirement) {
                        child.requirement = parentCell.requirement;
                    }

                    if (childIndex > 0) {
                        copyRow(table, rowNumber, rowNumber + childIndex);
                    }

                    table[rowNumber + childIndex][columnNumber] = child;
                }
                rowNumber += Math.max(children.length, 1); // If more than 1 child was added, then rows were added and we can skip them.
            }
        }
    }

    /**
     * @returns an array, never null
     */
    function getChildren(requirement, name) {
        // For example @author, @cost, @type...
        if (name.startsWith("@")) {
            const propertyName = name.substring(1);
            return [{
                type: propertyName === "author" ? "user" : "text",
                value: requirement.properties && requirement.properties[propertyName] || undefined
            }];
        }
        if (name === "dependencies") {
            return requirement.dependencies.map(dependencyKey => {
                let dependency = requirements.find(item => item.key === dependencyKey);
                return {
                    type: "requirement",
                    requirement: dependency
                }
            });
        }
        throw "Unknown property of requirement: " + name;
    }

    if (table.length) alert("Data is prepared, we'll start the rendering for " + (table.length * table[0].length) + " cells");

    return table;
}

if (document.getElementById("root-vanilla-js")) {
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
            document.getElementById("root-vanilla-js").innerHTML = html;
        });
}