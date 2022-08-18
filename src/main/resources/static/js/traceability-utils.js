
export const listOfColumns = [
    "requirement",
    "@author",
    "dependencies",
    "@author",
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
        for (let rowNumber = 0; rowNumber < table.length; ) {
            let parentCell = table[rowNumber][columnNumber - 1];
            let children = getChildren(parentCell.requirement, columnName);
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
            rowNumber += children.length; // If more than 1 child was added, then rows were added and we can skip them.
        }
    }

    function getChildren(requirement, name) {
        switch (name) {
            case "@author": {
                return [{
                    type: "user",
                    value: requirement.properties && requirement.properties["author"] || undefined
                }]
            }
            case "@cost": {
                return [{
                    type: "text",
                    value: requirement.properties && requirement.properties["cost"] ? requirement.properties["cost"] + " €" : undefined
                }]
            }
            case "dependencies": {
                return requirement.dependencies.map(dependencyKey => {
                    let dependency = requirements.find(item => item.key === dependencyKey);
                    return {
                        type: "requirement",
                        requirement: dependency
                    }
                });
            }
        }
    }

    if (table.length) alert("Starting the rendering for " + (table.length * table[0].length) + " cells");

    return table;
}