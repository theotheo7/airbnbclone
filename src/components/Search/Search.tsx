import './Search.css';
import {
    Checkbox,
    DatePicker,
    DefaultButton,
    Dropdown,
    IDropdownOption,
    Stack,
    StackItem,
    TextField
} from "@fluentui/react";
import React, {useEffect, useState} from "react";
import {IListingBasicDetails} from "../../models/Listing/IListingBasicDetails";
import {fetchResults} from "../../services/ResultsService";

function Search() {
    const [isResults, setIsResults] = useState<boolean>(false);

    const [page, setPage] = useState<number>(1)

    const [city, setCity] = useState<string>("");
    const [fromDate, setFromDate] = useState<Date | undefined>(undefined);
    const [toDate, setToDate] = useState<Date | undefined>(undefined);
    const [people, setPeople] = useState<number>(0);
    const [type, setType] = useState<string | null>(null);
    const [maxPrice, setMaxPrice] = useState<number | null>(null);
    const [wifi, setWifi] = useState<boolean>(false);
    const [AC, setAC] = useState<boolean>(false);
    const [heat, setHeat] = useState<boolean>(false);
    const [kitchen, setKitchen] = useState<boolean>(false);
    const [TV, setTV] = useState<boolean>(false);
    const [parking, setParking] = useState<boolean>(false);
    const [elevator, setElevator] = useState<boolean>(false);

    const [results, setResults] = useState<IListingBasicDetails[]>([]);

    useEffect(() => {
        if (isResults) {
            let urlQuery = "";

            const fromMonth = fromDate?.getMonth() ? fromDate?.getMonth() + 1 : 1;
            const toMonth = toDate?.getMonth() ? toDate?.getMonth() + 1 : 1;

            const fromDay = fromDate?.getDate() ? fromDate?.getDate() : 1;
            const toDay = toDate?.getDate() ? toDate?.getDate() : 1;

            const fDate = fromDate?.getFullYear() + (fromMonth < 10 ? "-0" + fromMonth : "-" + fromMonth) + (fromDay < 10 ? "-0" + fromDay : "-" + fromDay);
            const tDate = toDate?.getFullYear() + (toMonth < 10 ? "-0" + toMonth : "-" + toMonth) + (toDay < 10 ? "-0" + toDay : "-" + toDay)

            urlQuery = urlQuery + "&city=" + city + "&from-date=" + fDate + "&to-date=" + tDate + "&people=" + people;
            
            if (type)
                urlQuery = urlQuery + "&type=" + type;
            if (maxPrice)
                urlQuery = urlQuery + "&max-price=" + maxPrice;
            if (wifi)
                urlQuery = urlQuery + "&wifi=true";
            if (AC)
                urlQuery = urlQuery + "&ac=true";
            if (heat)
                urlQuery = urlQuery + "&heat=true";
            if (kitchen)
                urlQuery = urlQuery + "&kitchen=true";
            if (TV)
                urlQuery = urlQuery + "&tv=true";
            if (parking)
                urlQuery = urlQuery + "&parking=true";
            if (elevator)
                urlQuery = urlQuery + "&elevator=true";
            
            (async function() {
                console.log("I AM HERE!");
                const result = await fetchResults(urlQuery, page);
                console.log(result);
                setResults(result);
            })();
        }
    }, [AC, TV, city, elevator, fromDate, heat, isResults, kitchen, maxPrice, page, parking, people, toDate, type, wifi]);

    const typeOptions: IDropdownOption[] = [
        { key: 'Room', text: 'Private room' },
        { key: 'Home', text: 'Entire home' }
    ];

    function _onChangeCity(
        _event: React.FormEvent<HTMLInputElement | HTMLTextAreaElement>,
        newValue?: string
    ): void {
        if (newValue) {
            setCity(newValue);
        } else {
            setCity("");
        }
    }

    function _onChangePeople(
        _event: React.FormEvent<HTMLInputElement | HTMLTextAreaElement>,
        newValue?: string
    ): void {
        if (Number(newValue) > -1) {
            setPeople(Number(newValue));
        }
    }

    const _onChangeType = (event: React.FormEvent<HTMLDivElement>, option: IDropdownOption | undefined): void => {
        setType(option?.key as string);
    }

    function _onChangeMaxPrice(
        _event: React.FormEvent<HTMLInputElement | HTMLTextAreaElement>,
        newValue?: string
    ): void {
        if (Number(newValue) > -1) {
            setMaxPrice(Number(newValue));
        }
    }

    function _onChangeWifi() {
        setWifi(!wifi);
    }

    function _onChangeAC() {
        setAC(!AC);
    }

    function _onChangeHeat() {
        setHeat(!heat);
    }

    function _onChangeKitchen() {
        setKitchen(!kitchen);
    }

    function _onChangeTV() {
        setTV(!TV);
    }

    function _onChangeParking() {
        setParking(!parking);
    }

    function _onChangeElevator() {
        setElevator(!elevator);
    }

    function _onClickNewSearch() {
        window.location.replace("https://localhost:8080");
    }

    function _onClickPrevious() {
        if (page > 1) {
            setPage(page - 1);
        }
    }

    function _onClickNext() {
        setPage(page + 1);
    }

    return (!isResults ?
        <div className="search-container">
            <Stack className="search-box">
                <StackItem>
                    <h3>Where to?</h3>
                </StackItem>
                <StackItem className="field-row">
                    <TextField label="City" value={city} onChange={_onChangeCity}/>
                    <DatePicker
                        label="From"
                        value={fromDate}
                        onSelectDate={(setFromDate as (date: Date | null | undefined) => void)}
                        style={{minWidth: '10vw'}}
                    />
                    <DatePicker
                        label="To"
                        value={toDate}
                        onSelectDate={(setToDate as (date: Date | null | undefined) => void)}
                        style={{minWidth: '10vw'}}
                    />
                    <TextField label="People" value={people.toString()} type="number" onChange={_onChangePeople}/>
                </StackItem>
                <StackItem>
                    <h3>Extras</h3>
                </StackItem>
                <StackItem className="field-row">
                    <Dropdown label="Room type" options={typeOptions} onChange={_onChangeType} style={{minWidth: '10vw'}}/>
                    <TextField label="Max price" type="number" onChange={_onChangeMaxPrice}/>
                </StackItem>
                <StackItem className="field-row" style={{paddingTop: '20px'}}>
                    <Checkbox label="WiFi" onChange={_onChangeWifi}/>
                    <Checkbox label="AC" onChange={_onChangeAC}/>
                    <Checkbox label="Heat" onChange={_onChangeHeat}/>
                    <Checkbox label="Kitchen" onChange={_onChangeKitchen}/>
                    <Checkbox label="TV" onChange={_onChangeTV}/>
                    <Checkbox label="Parking" onChange={_onChangeParking}/>
                    <Checkbox label="Elevator" onChange={_onChangeElevator}/>
                </StackItem>
                <StackItem className="button-row">
                    <DefaultButton text="Search" onClick={() => setIsResults(true)}/>
                </StackItem>
            </Stack>
        </div>
            :
        <div>
            <Stack className="results-container">
                <StackItem className="new-search-button">
                    <DefaultButton text="New Search" onClick={_onClickNewSearch}/>
                </StackItem>
                {results.length > 0 ?
                    <div>
                        <StackItem className="page-buttons">
                            <DefaultButton text="Previous" onClick={_onClickPrevious}/>
                            {page}
                            <DefaultButton text="Next" onClick={_onClickNext}/>
                        </StackItem>
                        <StackItem>
                            {results.map((result) => (
                                <div className="listing-details-row" key={result.name}>
                                    <img src={require("../../../../airbnbclone-backend/" + result.image)} alt={result.name} width={100} height={100}/>
                                    {result.name}
                                    <DefaultButton text="View"/>
                                </div>
                            ))}
                        </StackItem>
                        <StackItem className="page-buttons">
                            <DefaultButton text="Previous" onClick={_onClickPrevious}/>
                            {page}
                            <DefaultButton text="Next" onClick={_onClickNext}/>
                        </StackItem>
                    </div>
                    :
                    <div className="no-results-message">No results! Search again.</div>
                }
            </Stack>
        </div>
    );
}

export default Search;