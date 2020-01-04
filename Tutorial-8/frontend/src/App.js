import React from 'react';
import './App.css';
import List from "./components/List";
import dummyItems from "./items.json";

export default class App extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            favItems: []
        };
    }

    handleItemClick = item => {
        const newItems = [...this.state.favItems];
        const newItem = {...item};
        const targetInd = newItems.findIndex(it => it.id === newItem.id);
        if (targetInd < 0) {
            newItems.push(newItem);
        } else {
            newItems.splice(targetInd, 1);
        }
        this.setState({favItems: newItems});
    };

    render() {
        const {favItems} = this.state;
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
                                onItemClick={this.handleItemClick}
                            />
                        </div>
                        <div className="col-sm">
                            <List
                                title="My Favorite"
                                items={favItems}
                                onItemClick={this.handleItemClick}
                            />
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}
