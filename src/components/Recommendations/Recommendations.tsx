import './Recommendations.css'
import {useEffect, useState} from "react";
import {IListingBasicDetails} from "../../models/Listing/IListingBasicDetails";
import {fetchRecommendations} from "../../services/GuestService";
import {Stack, StackItem} from "@fluentui/react";

function Recommendations() {
    const [recommendations, setRecommendations] = useState<IListingBasicDetails[]>([]);

    useEffect(() => {
        (async function() {
            const result = await fetchRecommendations();
            setRecommendations(result);
            console.log(result);
        })();
    }, []);

    return (
        <div>
            {recommendations.length > 0 ?
                <Stack>
                    <StackItem className="listings-list-container">
                        {recommendations.map((recommendation) => (
                            <div className="listing-details-row" key={recommendation.name}>
                                <img src={require("../../../../airbnbclone-backend/" + recommendation.image)} alt={recommendation.name} width={100} height={100}/>
                                <div>{recommendation.name}</div>
                            </div>
                        ))}
                    </StackItem>
                </Stack>
                :
                <div>
                    No available recommendations!
                </div>
            }
        </div>
    );
}

export default Recommendations;