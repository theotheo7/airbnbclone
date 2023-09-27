export interface ILocation {
    longitude: number;
    latitude: number;
    address: string;
    city: string;
    state: string;
    zipcode: string;
}

export class Location implements ILocation {
    longitude: number;
    latitude: number;
    address: string;
    city: string;
    state: string;
    zipcode: string;

    constructor(
        longitude: number,
        latitude: number,
        address: string,
        city: string,
        state: string,
        zipcode: string,
    ) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
    }
}