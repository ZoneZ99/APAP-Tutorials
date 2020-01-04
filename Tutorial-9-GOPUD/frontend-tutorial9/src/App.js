import React, {Component} from 'react';
import './App.css';
import Layout from "./components/Layout/Layout"
import Restorans from "./containers/Restorans/Restorans";

class App extends Component {
    render() {
        return (
            <React.Fragment>
                <Layout>
                    <Restorans />
                </Layout>
            </React.Fragment>
        );
    }
}

export default App;
