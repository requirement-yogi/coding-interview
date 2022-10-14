import React from "react";
import ReactDOM from "react-dom";
import {Navbar} from "./Navbar";
import {Page} from "./Page";
import {adfExample1} from "./page-adf";

const ADFTextArea = () => {

    const adf1 = JSON.parse(JSON.stringify(adfExample1));
    const adf2 = JSON.parse(JSON.stringify(adfExample1));


    return (
        <>
            <p>
                ADF is the Atlassian Document Format, it's based on the spirit of HTML, but represented as JSON.
                It renders <a href={"/adf-page-rendering.png"}>like this</a>.
            </p>

            <div id="page-adf-comparator" style={{ width: "100%", display: "flex" }}>
                <pre style={{ width: "45%" }}>{JSON.stringify(adf1, null, 2)}</pre>
                <pre style={{ width: "45%" }}>{JSON.stringify(adf2, null, 2)}</pre>
            </div>
        </>
    );
};

ReactDOM.render(
<>
    <Navbar />
    <Page>
        <h3>ADF Comparison</h3>
        <ADFTextArea/>
    </Page>
</>, document.getElementById("root"));