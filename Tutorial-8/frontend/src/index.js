import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import AppClass from './App';
import AppFunc from "./AppFunc";
import * as serviceWorker from './serviceWorker';

ReactDOM.render(
    <React.Fragment>
        <div className="mt-5" />
        <AppClass />
        <div className="mt-5 mb-5" />
        <AppFunc />
        <div className="mt-5 mb-5" />
    </React.Fragment>,
    document.getElementById('root')
);

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
