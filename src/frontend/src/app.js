import React, {useEffect, useState} from "react";
import ReactDOM from "react-dom";
import DynamicTable from '@atlaskit/dynamic-table';
import Lozenge from '@atlaskit/lozenge';
import Textfield from '@atlaskit/textfield';
import Button from '@atlaskit/button';
import SearchIcon from '@atlaskit/icon/glyph/search';
import {Footer} from "./Footer";
import {Navbar} from "./Navbar";
import {Page} from "./Page";


const urlPersons = "/persons";
const urlFolks = "/folks";
const urlUsers = "/users";
const urlPeople = "/people";

//Sort users by status => ACTIVE => SUSPENDED => INACTIVE

const UsersView = () => {
    const [users, setUsers] = useState([]);
    const [query, setQuery] = useState("");

    useEffect(() => {
        getUsers({ q: '@' });
    }, []);

    const getUsers = ({ q }) => {
        let url = urlPersons;
        if (q) {
            url += `?q=${encodeURI(q)}`;
        }
        fetch(url)
            .then(result => result.json())
            .then(users => setUsers(users));
    };

    const onStatusClick = async ({ id, status }) => {
        const result = await fetch(`${urlFolks}/${id}`, {
            method: 'PUT',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: status
        });
        const user = await result.json();
        setUsers(users =>
            users.map(({ id, status, ...rest }) => ({
                ...rest,
                id,
                status: id === user.id ? user.status : status
            }))
        );
    };

    const onSearch = ({ query }) => getUsers({ q: query });

    return (
        <>
            <SearchBar query={query} setQuery={setQuery} onSearch={onSearch}/>
            <Users users={users} onClick={onStatusClick} />
        </>
    );
};

const SearchBar = ({ onSearch, query, setQuery }) => {
    return (
        <div>
            <Textfield
                autoComplete="on"
                width="xlarge"
                placeholder="Search for an email here..."
                value={query}
                onChange={e => {
                    setQuery(e.target.value);
                }}
                elemAfterInput={
                    <Button onClick={() => onSearch({ query })} type="submit" style={{marginRight: '2px'}} iconAfter={<SearchIcon/>}/>
                }
                onKeyPress={e => {
                    if (e.key === "Enter") {
                        onSearch({ query });
                    }
                }}
            />
        </div>
    )
}

const Users = ({ users = [], onClick }) => {
    return (
        <div style={{ width: '1000px' }}>
            <DynamicTable
                isFixedSize
                caption="List of candidates"
                head={{
                    cells: ["Name", "Email", "Application status"].map((header, index) => ({
                        key: index,
                        content: header
                    }))
                }}
                rows={users.map(({ id, name, email, status }) => {
                    status = {
                        "ACCEPTED": "Accepted",
                        "INPROGRESS": "In progress",
                        "REJECTED": "Rejected"
                    }[status];

                    return ({
                        cells: [{
                            key: 0,
                            content: name
                        }, {
                            key: 1,
                            content: email
                        }, {
                            key: 2,
                            content: <Status status={status} onClick={() => onClick({ id, status })}/>
                        }]
                    });
                })}
            />
        </div>
    )
};

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
        <UsersView/>
    </Page>
    <Footer />
</>, document.getElementById("root"));
