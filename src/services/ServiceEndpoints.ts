enum ServiceEndpoints {
    // Authorization
    Register = "https://localhost:8443/api/v1/auth/register",
    Authenticate = "https://localhost:8443/api/v1/auth/authenticate",

    // Listing
    CreateListing = "https://localhost:8443/api/listing/create",
    DeleteListing = "https://localhost:8443/api/listing/delete"
}

export default ServiceEndpoints;