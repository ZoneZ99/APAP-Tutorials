import React from 'react';
import './App.css';
import List from "./components/List";
import dummyItems from "./items.json";

export default class App extends React.Component {
    render() {
        return (
            <div className="container-fluid">
                <h1 className="text-center">
                    Welcome!
                    <small>Class-based</small>
                </h1>
                <div className="container pt-3">
                    <div className="row">
                        <div className="col-sm">
                            <List
                                title="Our Menu"
                                items={dummyItems}
                                onItemClick={() => {
                                }}
                            />
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}
