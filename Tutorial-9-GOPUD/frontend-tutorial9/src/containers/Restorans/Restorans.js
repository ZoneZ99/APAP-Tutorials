import React, {Component} from "react";
import Restoran from "../../components/Restoran/Restoran";
import classes from "./Restorans.module.css"

class Restorans extends Component {

    constructor(props) {
        super(props);
        this.state = {
            restorans: [
                {id:1, nama: "Restoran A", alamat: "Address of Restoran A", nomorTelepon: "123"},
                {id:2, nama: "Restoran B", alamat: "Address of Restoran B", nomorTelepon: "456"},
                {id:3, nama: "Restoran C", alamat: "Address of Restoran C", nomorTelepon: "789"},
            ],
            isLoading: false
        };
    }

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