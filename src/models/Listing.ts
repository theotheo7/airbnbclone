import { ILocation } from "./Location";

export interface IListing {
    name: string;
    location: ILocation;
}

export class Listing implements IListing {
    name: string;
    location: ILocation;

    constructor(listing: IListing) {
        this.name = listing.name;
        this.location = listing.location;
    }
}