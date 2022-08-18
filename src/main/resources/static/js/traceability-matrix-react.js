import React, {useEffect, useState} from "react";
import ReactDOM from "react-dom";
import Lozenge from '@atlaskit/lozenge';
import {Navbar} from "./Navbar";
import {Page} from "./Page";
import {buildTraceabilityMatrixTable, listOfColumns} from "./traceability-matrix-vanilla";

const RequirementsView = () => {
    const [cells, setCells] = useState([]);
    const [query, setQuery] = useState("");

    useEffect(() => {
        fetch("/rest/requirements")
            .then(async result => setCells(await result.json()));
    }, []);

    return (
        <>
            <TraceabilityMatrix requirements={cells} columns={ listOfColumns }/>
        </>
    );
};

const TraceabilityMatrix = ({ requirements, columns }) => {

    let table = buildTraceabilityMatrixTable(requirements, columns);

    return (
        <table className={"aui"} style={{ width: "auto" }}>
            <thead>
            <tr>
                {columns.map(column => <th>{column}</th>)}
            </tr>
            </thead>
            <tbody>
            {table.map(row =>
                <tr>
                    {row.map(cell => render(cell))}
                </tr>
            )}
            </tbody>
        </table>
    )
};

function render(cell) {
    if (cell.subsequentRow) {
        return <td className={"subsequent-row"}/>;
    }
    switch (cell && cell.type) {
        case "user": return <td><UserRenderer cell={cell}/></td>;
        case "text": return <td>{cell.value || ""}</td>;
        case "requirement": return <td><RequirementRenderer cell={cell}/></td>;
    }
    return <td>Unknown cell type: {cell.type}</td>;
}

const UserRenderer = ({ cell }) => {
    return <>{ cell.value }</>
}

let countRequests = 0;
const RequirementRenderer = ({ cell }) => {
    const [ requirementStatus, setRequirementStatus ] = useState(null);
    useEffect(() => {
        if (++countRequests < 10) {
            fetch("/rest/requirements/" + cell.requirement.key + "/status")
                .then(async result => {
                    setRequirementStatus(await result.text());
                });
        } else if (countRequests === 10) {
            console.error("Too many requests");
        }
    });
    return <>
        <a href={"/requirements/" + cell.requirement.key }>{cell.requirement.key}</a>
        {' '}
        { requirementStatus && <Status status={requirementStatus}/>}
    </>;
}

const getCSSClass = ({ status }) => ({
    "ACCEPTED":    "success",
    "IN PROGRESS": "inprogress",
    "REJECTED":    "removed"
})[status];

const Status = ({ status, onClick }) => {
    return (
        <div style={{ width: 'fit-content', cursor: 'pointer' }} onClick={onClick}>
            <Lozenge isBold appearance={getCSSClass({ status })}>{ status }</Lozenge>
        </div>
    );
}

ReactDOM.render(
<>
    <Navbar />
    <Page>
        <h3>"React" traceability matrix</h3>
        <p>
            A "traceability" matrix is a cascading table where we can see requirements, their dependencies,
            the dependencies of their dependencies, etc.
        </p>
        <RequirementsView/>
    </Page>
</>, document.getElementById("root"));