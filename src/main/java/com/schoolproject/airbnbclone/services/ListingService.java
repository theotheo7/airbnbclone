package com.schoolproject.airbnbclone.services;

import com.ctc.wstx.api.WstxOutputProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.schoolproject.airbnbclone.dtos.listing.request.ListingRequest;
import com.schoolproject.airbnbclone.dtos.listing.response.ListingBasicDetails;
import com.schoolproject.airbnbclone.dtos.listing.response.ListingCompleteDetails;
import com.schoolproject.airbnbclone.exceptions.ListingException;
import com.schoolproject.airbnbclone.exceptions.UserException;
import com.schoolproject.airbnbclone.models.Listing;
import com.schoolproject.airbnbclone.models.User;
import com.schoolproject.airbnbclone.repositories.ListingRepository;
import com.schoolproject.airbnbclone.repositories.UserRepository;
import com.schoolproject.airbnbclone.specifications.ListingSpecificationBuilder;
import com.schoolproject.airbnbclone.views.json.ListingJSON;
import com.schoolproject.airbnbclone.views.xml.ListingXML;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.TimeZone;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ListingService {

    private final ListingRepository listingRepository;
    private final UserRepository userRepository;
    private final ImageService imageService;
    private final AvailabilityService availabilityService;
    private final SearchHistoryService searchHistoryService;

    @Transactional
    public void createListing(Authentication authentication, ListingRequest listingRequest, MultipartFile[] multipartFiles) {
        Optional<User> host = userRepository.findByUsername(authentication.getName());
        var listing = Listing.builder()
                .name(listingRequest.getName())
                .location(listingRequest.getLocation())
                .host(host.orElseGet(User::new))
                .maxPeople(listingRequest.getMaxPeople())
                .price(listingRequest.getPrice())
                .extraPeople(listingRequest.getExtraPeople())
                .type(listingRequest.getType())
                .beds(listingRequest.getBeds())
                .baths(listingRequest.getBaths())
                .meters(listingRequest.getMeters())
                .living(listingRequest.getLiving())
                .party(listingRequest.getParty())
                .pets(listingRequest.getPets())
                .summary(listingRequest.getSummary())
                .wifi(listingRequest.getWifi())
                .ac(listingRequest.getAc())
                .heat(listingRequest.getHeat())
                .kitchen(listingRequest.getKitchen())
                .tv(listingRequest.getTv())
                .parking(listingRequest.getParking())
                .elevator(listingRequest.getElevator())
                .build();
        listingRepository.save(listing);
        this.imageService.uploadListingImages(listing, multipartFiles);
        this.availabilityService.populateAvailability(listing, listingRequest.getStartDate(), listingRequest.getEndDate());
    }

    public List<ListingBasicDetails> getListings(Authentication authentication, Integer page) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<Listing> listings = listingRepository.findAllByHostName(pageable, authentication.getName());
        List<Listing> listingList = listings.getContent();
        return listingList.stream()
                .map(ListingBasicDetails::new)
                .collect(Collectors.toList());
    }

    public ListingCompleteDetails getListing(Authentication authentication, Long id) {
        Optional<Listing> optionalListing = this.listingRepository.findById(id);

        if (optionalListing.isEmpty()) {
            throw new ListingException(id.toString(), ListingException.LISTING_NOT_FOUND, HttpStatus.NOT_FOUND);
        }

        Listing listing = optionalListing.get();

        if (authentication != null && !Objects.equals(authentication.getName(), listing.getHost().getUsername())) {
            Optional<User> optionalUser = this.userRepository.findByUsername(authentication.getName());
            if (optionalUser.isEmpty()) {
                throw new UserException(UserException.USER_NOT_FOUND, HttpStatus.NOT_FOUND);
            }
            this.searchHistoryService.recordUserInteraction(optionalUser.get(), listing);
        }

        return new ListingCompleteDetails(listing);
    }

    public void updateListing(Authentication authentication, ListingRequest listingRequest, MultipartFile[] multipartFiles) {
        Optional<Listing> optionalListing = listingRepository.findByName(listingRequest.getName());

        if (optionalListing.isEmpty()) {
            throw new ListingException(ListingException.LISTING_NOT_FOUND, HttpStatus.NOT_FOUND);
        }

        Listing listing = optionalListing.get();

        if (!listing.getHost().getUsername().equals(authentication.getName())) {
            throw new ListingException(ListingException.LISTING_HOST_DIFFERENT, HttpStatus.BAD_REQUEST);
        }

        listing.setLocation(listingRequest.getLocation());
        listing.setName(listingRequest.getName());
        listing.setMaxPeople(listingRequest.getMaxPeople());
        listing.setPrice(listingRequest.getPrice());
        listing.setExtraPeople(listingRequest.getExtraPeople());
        listing.setType(listingRequest.getType());
        listing.setBeds(listingRequest.getBeds());
        listing.setBaths(listingRequest.getBaths());
        listing.setMeters(listingRequest.getMeters());
        listing.setLiving(listingRequest.getLiving());
        listing.setParty(listingRequest.getParty());
        listing.setPets(listingRequest.getPets());
        listing.setSummary(listingRequest.getSummary());

        this.listingRepository.save(listing);

        imageService.updateListingImages(listing, multipartFiles);
    }

    public List<ListingBasicDetails> searchListings(
            Integer page,
            String city,
            LocalDate fromDate,
            LocalDate toDate,
            Integer people,
            String type,
            BigDecimal maxPrice,
            Boolean wifi,
            Boolean ac,
            Boolean heat,
            Boolean kitchen,
            Boolean tv,
            Boolean parking,
            Boolean elevator
    ) {
        PageRequest pageRequest = PageRequest.of(page, 10, Sort.by("price").ascending());

        Specification<Listing> listingSpecification = ListingSpecificationBuilder.filterListings(city, fromDate, toDate, people, type, maxPrice, wifi, ac, heat, kitchen, tv, parking, elevator);

        Page<Listing> listingPage = this.listingRepository.searchListings(listingSpecification, pageRequest);

        List<Listing> listingList = listingPage.getContent();

        System.out.println(listingList.size());

        return listingList.stream()
                .map(ListingBasicDetails::new)
                .collect(Collectors.toList());
    }

    public void deleteListing(Integer Id) {
        listingRepository.removeListingById(Id);
    }

    public ResponseEntity<InputStreamResource> export(Boolean asJSON) throws JsonProcessingException {
        HttpHeaders httpHeaders = new HttpHeaders();
        InputStream stringInputStream;
        InputStreamResource inputStreamResource;

        final Integer EXPORT_MAX_RESULTS = 500;

        List<Listing> listings = this.listingRepository.export(EXPORT_MAX_RESULTS);

        if (asJSON) {
            ObjectMapper objectMapper = getJSONMapper(TimeZone.getDefault());
            List<ListingJSON> listingJSONList = listings.stream()
                    .map(ListingJSON::new)
                    .toList();
            String jsonString = objectMapper.writeValueAsString(listingJSONList);
            stringInputStream = new ByteArrayInputStream(jsonString.getBytes());
            inputStreamResource = new InputStreamResource(stringInputStream);
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            httpHeaders.setContentDispositionFormData("attachment", "ExportListings.json");
        } else {
            XmlMapper xmlMapper = getXMLMapper(TimeZone.getDefault());
            List<ListingXML> listingXMLList = listings.stream()
                    .map(ListingXML::new)
                    .toList();
            String xmlString = xmlMapper.writeValueAsString(listingXMLList);
            stringInputStream = new ByteArrayInputStream(xmlString.getBytes());
            inputStreamResource = new InputStreamResource(stringInputStream);
            httpHeaders.setContentType(MediaType.APPLICATION_XML);
            httpHeaders.setContentDispositionFormData("attachment", "ExportListings.xml");
        }
        return new ResponseEntity<>(inputStreamResource, httpHeaders, HttpStatus.OK);
    }

    private XmlMapper getXMLMapper(TimeZone timeZone) {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.getFactory().getXMLOutputFactory().setProperty(WstxOutputProperties.P_AUTOMATIC_END_ELEMENTS, false);
        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
        xmlMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);
        xmlMapper.setTimeZone(timeZone);
        return xmlMapper;
    }

    private ObjectMapper getJSONMapper(TimeZone timeZone) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.setTimeZone(timeZone);
        return objectMapper;
    }
}
