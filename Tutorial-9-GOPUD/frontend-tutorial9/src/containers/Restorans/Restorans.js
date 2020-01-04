import React, {Component} from "react";
import Restoran from "../../components/Restoran/Restoran";
import classes from "./Restorans.module.css"
import axios from "../../axios-restoran";
import Modal from "../../components/UI/Modal/Modal";
import Button from "../../components/UI/Button/Button";

class Restorans extends Component {

    constructor(props) {
        super(props);
        this.state = {
            restorans: [],
            isCreate: false,
            isEdit: false,
            isLoading: false,
            nama: "",
            alamat: "",
            nomorTelepon: "",
            rating: ""
        };
    }

    componentDidMount() {
        this.loadRestorans();
    }

    addRestoranHandler = () => {
        this.setState({isCreate: true});
    };

    cancelledHandler = () => {
        this.setState({isCreate: false, isEdit: false});
    };

    changeHandler = event => {
        const {name, value} = event.target;
        this.setState({[name]: value});
    };

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

    submitAddRestoranHandler = event => {
        event.preventDefault();
        this.setState({isLoading: true});
        this.addRestoran();
        this.cancelledHandler();
        this.setState({
            nama: "",
            alamat: "",
            nomorTelepon: "",
            rating: ""
        })
    };

    async addRestoran() {
        const restoranToAdd = {
            nama: this.state.nama,
            alamat: this.state.alamat,
            nomorTelepon: this.state.nomorTelepon,
            rating: this.state.rating
        };
        await axios.post("/restoran", restoranToAdd);
        await this.loadRestorans();
    }

    renderForm() {
        const {isEdit} = this.state;
        return (
            <form>
                <input
                    className={classes.Input}
                    name="nama"
                    type="text"
                    placeholder="Nama"
                    value={this.state.nama}
                    onChange={this.changeHandler}
                />
                <input
                    className={classes.Input}
                    name="nomorTelepon"
                    type="number"
                    placeholder="Nomor Telepon"
                    value={this.state.nomorTelepon}
                    onChange={this.changeHandler}
                />
                <textarea
                    className={classes.TextArea}
                    name="alamat"
                    placeholder="Alamat"
                    value={this.state.alamat}
                    onChange={this.changeHandler}
                />
                <input
                    className={classes.Input}
                    name="rating"
                    type="number"
                    placeholder="Rating"
                    value={this.state.rating}
                    onChange={this.changeHandler}
                />
                <Button
                    btnType="Danger"
                    onClick={this.cancelledHandler}
                >
                    CANCEL
                </Button>
                <Button
                    btnType="Success"
                    onClick={isEdit ? this.submitEditRestoranHandler :
                        this.submitAddRestoranHandler}
                >
                    SUBMIT
                </Button>
            </form>
        );
    };

    editRestoranHandler(restoran) {
        this.setState({
            isEdit: true,
            idRestoran: restoran.idRestoran,
            nama: restoran.nama,
            nomorTelepon: restoran.nomorTelepon,
            rating: restoran.rating,
            alamat: restoran.alamat
        });
    }

    submitEditRestoranHandler = event => {
        console.log("editing")
        event.preventDefault();
        this.setState({isLoading: true});
        this.editRestoran();
        this.cancelledHandler();
    };

    async editRestoran() {
        const restoranToEdit = {
            idRestoran: this.state.idRestoran,
            nama: this.state.nama,
            alamat: this.state.alamat,
            nomorTelepon: this.state.nomorTelepon,
            rating: this.state.rating
        };
        await axios.put("/restoran/" + this.state.idRestoran, restoranToEdit);
        await this.loadRestorans();
        this.cancelledHandler();
    };

    async deleteRestoranHandler(restoranId) {
        await axios.delete(`/restoran/${restoranId}`);
        await this.loadRestorans();
    }

    render() {
        return (
            <React.Fragment>
                <Modal show={this.state.isCreate || this.state.isEdit}
                       modalClosed={this.cancelledHandler}
                >
                    {this.renderForm()}
                </Modal>
                <div className={classes.Title}>All Restorans</div>
                <div className={classes.ButtonLayout}>
                    <button
                        className={classes.AddRestoranButton}
                        onClick={this.addRestoranHandler}
                    >
                        + Add New Restoran
                    </button>
                </div>
                <div className={classes.Restorans}>
                    {this.state.restorans.map(restoran =>
                        <Restoran
                            key={restoran.id}
                            nama={restoran.nama}
                            alamat={restoran.alamat}
                            nomorTelepon={restoran.nomorTelepon}
                            edit={() => this.editRestoranHandler(restoran)}
                            delete={() => this.deleteRestoranHandler(restoran.idRestoran)}
                        />
                    )}
                </div>
            </React.Fragment>
        );
    }
}

export default Restorans;