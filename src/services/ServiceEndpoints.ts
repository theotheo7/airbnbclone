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
    Export = "https://localhost:8443/api/admin/export",

    // Host
    FetchListings = "https://localhost:8443/api/host/listings",
    FetchListing = "https://localhost:8443/api/host/listing",
    CreateListing = "https://localhost:8443/api/host/listing/create",
    UpdateListing = "https://localhost:8443/api/host/listing",

    // Guest
    Book = "https://localhost:8443/api/guest/book",
    FetchRecommendations = "https://localhost:8443/api/recommendation/recommend",

    // Search
    FetchResults = "https://localhost:8443/api/search/listings",
    FetchListingFromSearch = "https://localhost:8443/api/search/listing",

    // Messages
    SendMessage = "https://localhost:8443/api/user/messages",
    FetchMessages = "https://localhost:8443/api/user/messages",
    DeleteMessage = "https://localhost:8443/api/user/messages",
    ReadMessage = "https://localhost:8443/api/user/messages"
}

export default ServiceEndpoints;