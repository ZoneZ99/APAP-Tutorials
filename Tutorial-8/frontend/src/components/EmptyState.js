import React from "react";

export default function EmptyState(props) {
    return (
        <React.Fragment>
            <h5 className="h5"><em>Belum ada item yang dipilih</em></h5>
            <h6 className="h6">Klik salah satu item di daftar Menu</h6>
        </React.Fragment>
    );
}