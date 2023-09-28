enum ServiceEndpoints {
    // Authorization
    Register = "https://localhost:8443/api/v1/auth/register",
    Authenticate = "https://localhost:8443/api/v1/auth/authenticate",

    // Admin
    FetchUsers = "https://localhost:8443/api/admin/users",
    FetchUser = "https://localhost:8443/api/admin/user",
    ApproveHost = "https://localhost:8443/api/admin/users/approve",

    // Host
    FetchListings = "https://localhost:8443/api/host/listings",
    FetchListing = "https://localhost:8443/api/host/listing",
    CreateListing = "https://localhost:8443/api/host/listing/create",
    UpdateListing = "https://localhost:8443/api/host/listing"
}

export default ServiceEndpoints;