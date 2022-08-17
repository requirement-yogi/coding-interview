import React from "react";

const css = {
    justifyContent: 'center',
    display: 'flex',
    alignItems: 'center'
};


export const Footer = () => (
    <footer style={{
        display: 'flex',
        justifyContent: 'center',
        width: '100%',
        position: 'absolute',
        bottom: 0,
        height: '50px',
        boxShadow: '0px -2px 8px -6px #000000',
        backgroundColor: '#FAFBFC',
        fontSize: '12px'
    }}>
        <div style={css}>
            <a style={{textDecoration: 'none', color: '#5E6C84'}} href="https://www.requirementyogi.com/"
               target="_blank">
                Requirement Yogi
            </a>
        </div>
        <div style={{...css, marginLeft: '15px', color: '#5E6C84'}}>
            Coding interview
        </div>
    </footer>
);