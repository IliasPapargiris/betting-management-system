package com.accepted.sports_betting.utils;

public class JsonExamples {

    // Example of MatchRequestDTO JSON
    public static final String MATCH_REQUEST_JSON = """
            {
                "description": "Derby Match",
                "matchDate": "2024-12-01",
                "matchTime": "20:30:00",
                "teamA": "OSFP",
                "teamB": "PAO",
                "sport": "FOOTBALL"
            }""";

    // Example of MatchResponseDTO JSON
    public static final String MATCH_RESPONSE_JSON = """
            {
                "id": 1,
                "description": "Derby Match",
                "matchDate": "2024-12-01",
                "matchTime": "20:30:00",
                "teamA": "OSFP",
                "teamB": "PAO",
                "sport": "FOOTBALL"
            }""";

    // Example of Validation Error JSON for MatchRequestDTO
    public static final String VALIDATION_ERROR_JSON = """
            {
                "error": "Validation Failed",
                "messages": [
                    "description: Description is required.",
                    "matchDate: Match date must be in the future.",
                    "teamA: Team A is required.",
                    "teamB: Team B is required.",
                    "sport: Sport is required."
                ],
                "status": 400,
                "timestamp": "2024-11-07T20:47:20.120353"
            }""";

    public static final String MATCH_CONFLICT_ERROR_JSON = """
            {
                "error": "Conflict",
                "message": "A match is already scheduled for either team OSFP or PAO on 2024-11-14",
                "status": 409,
                "timestamp": "2024-11-07T21:04:56.444467"
            }""";

    // Example of a "Match Not Found" error JSON
    public static final String MATCH_NOT_FOUND_ERROR_JSON = """
            {
                "error": "Match Not found",
                "status": 404,
                "timestamp": "2024-11-07T21:05:00.000000"
            }""";


    // Example of Pageable Match List JSON
    public static final String MATCHES_PAGEABLE_JSON = """
            {
                "content": [
                    {
                        "id": 1,
                        "description": "Derby",
                        "matchDate": "2024-12-01",
                        "matchTime": "20:30:00",
                        "teamA": "OSFP",
                        "teamB": "PAO",
                        "sport": "FOOTBALL"
                    },
                    {
                        "id": 2,
                        "description": "Derby",
                        "matchDate": "2024-12-05",
                        "matchTime": "18:00:00",
                        "teamA": "AEK",
                        "teamB": "PAOK",
                        "sport": "BASKETBALL"
                    }
                ],
                "pageable": {
                    "pageNumber": 0,
                    "pageSize": 20,
                    "sort": {
                        "empty": true,
                        "sorted": false,
                        "unsorted": true
                    },
                    "offset": 0,
                    "paged": true,
                    "unpaged": false
                },
                "last": true,
                "totalElements": 2,
                "totalPages": 1,
                "first": true,
                "size": 20,
                "number": 0,
                "sort": {
                    "empty": true,
                    "sorted": false,
                    "unsorted": true
                },
                "numberOfElements": 2,
                "empty": false
            }""";


    public static final String MATCH_ODDS_REQUEST_VALID_JSON = """
            {
                "matchId": 1,
                "specifier": "HOME_WIN",
                "odd": 1.75
            }""";

    // Example of a MatchOddsResponseDTO JSON
    public static final String MATCH_ODDS_RESPONSE_JSON = """
            {
                "id": 1,
                "matchId": 1,
                "specifier": "HOME_WIN",
                "odd": 1.75
            }""";

    // Example of a validation error response JSON for MatchOddsRequestDTO

    public static final String MATCH_ODDS_VALIDATION_ERROR_JSON = """
            {
                "error": "Validation Failed",
                "messages": [
                    "matchId: Match ID is required.",
                    "specifier: Specifier is required.",
                    "odd: Odd must be greater than zero."
                ],
                "status": 400,
                "timestamp": "2024-11-07T20:47:20.120353"
            }""";

    public static final String MATCH_ODDS_CONFLICT_ERROR_JSON = """
            {
                "error": "Conflict",
                "message": "Odds with specifier 'HOME_WIN' already exist for match ID 1.",
                "status": 409,
                "timestamp": "2024-11-07T22:33:20.461231"
            }""";

    public static final String MATCH_ODDS_LIST_EXAMPLE_JSON = """
[
    {
        "id": 1,
        "matchId": 1,
        "specifier": "HOME_WIN",
        "odd": 1.50
    },
    {
        "id": 2,
        "matchId": 1,
        "specifier": "DRAW",
        "odd": 3.20
    },
    {
        "id": 3,
        "matchId": 1,
        "specifier": "AWAY_WIN",
        "odd": 2.80
    }
]""";
}

