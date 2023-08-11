import { PrimaryButton } from '@fluentui/react';
import './App.css';
import { createListing } from '../services/ListingService';
import { Listing } from '../models/Listing';

const listing: Listing = {
    name: "test",
    location: {
        longitude: 2,
        latitude: 2,
        address: "test",
        city: "test",
        state: "test",
        zipcode: "test"
    }
};

async function onClick() {
    const result = await createListing(listing);
    console.log(result);
}
function App() {
    return (
        <div className="App">
            <PrimaryButton text="Create" onClick={onClick} allowDisabledFocus disabled={false} checked={false} />
        </div>
    );
}

export default App;
