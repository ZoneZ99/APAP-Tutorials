import React, {Component} from "react";
import Restoran from "../../components/Restoran/Restoran";
import classes from "./Restorans.module.css"
import axios from "../../axios-restoran";

class Restorans extends Component {

    constructor(props) {
        super(props);
        this.state = {
            restorans: [],
            isLoading: false
        };
    }

    componentDidMount() {
        this.loadRestorans();
    }

    loadRestorans = async () => {
        const fetchedRestorans = [];
        const response = await axios.get("/restorans");
        for (let key in response.data) {
            fetchedRestorans.push({
                ...response.data[key]
            });
        }
        this.setState({
            restorans: fetchedRestorans
        });
    };

    render() {
        return (
            <React.Fragment>
                <div className={classes.Title}>All Restorans</div>
                <div className={classes.Restorans}>
                    {this.state.restorans.map(restoran =>
                        <Restoran
                            key={restoran.id}
                            nama={restoran.nama}
                            alamat={restoran.alamat}
                            nomorTelepon={restoran.nomorTelepon}
                        />)}
                </div>
            </React.Fragment>
        );
    }
}

export default Restorans;