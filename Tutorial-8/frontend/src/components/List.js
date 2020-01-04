import React from "react";

import Item from "./Item";
import EmptyState from "./EmptyState";

export default function List({title, items, onItemClick, itemExtraProps}) {
    return (
        <React.Fragment>
            <h3 style={styles.heading}>{title}</h3>
            <div className="list-group">
                {items.length === 0 && <EmptyState />}
                {
                    items.map(item => (
                        <Item
                            key={item.id}
                            item={item}
                            onChange={onItemClick}
                            {...itemExtraProps}
                        />
                    ))
                }
            </div>
        </React.Fragment>
    );
}

const styles = {
    heading: {
        fontFamily: "courier new"
    }
};