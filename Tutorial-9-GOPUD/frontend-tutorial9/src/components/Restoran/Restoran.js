import React from "react";
import classes from "./Restoran.module.css";

const Restoran = props => {
    return (
        <div className={classes.Restoran}>
            <h3>
                {props.nama}
            </h3>
            <p>
                Alamat : {props.alamat}
            </p>
            <p>
                Nomor Telepon : {props.nomorTelepon}
            </p>
            <div>
                <button className={classes.EditRestoranButton} onClick={props.edit}>
                    Edit
                </button>
                <button className={classes.DeleteRestoranButton} onClick={props.delete}>
                    Delete
                </button>
            </div>
        </div>
    );
};

export default Restoran;