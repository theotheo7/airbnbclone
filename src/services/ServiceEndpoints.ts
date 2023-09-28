enum ServiceEndpoints {
    // User
    Register = "https://localhost:8443/api/user/register",
    Authenticate = "https://localhost:8443/api/user/authenticate",
    FetchMyProfile = "https://localhost:8443/api/user",
    UpdateMyProfile = "https://localhost:8443/api/user/update",

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