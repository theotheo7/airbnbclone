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

    constructor(location: ILocation) {
        this.longitude = location.longitude;
        this.latitude = location.latitude;
        this.address = location.address;
        this.city = location.city;
        this.state = location.state;
        this.zipcode = location.zipcode;
    }
}