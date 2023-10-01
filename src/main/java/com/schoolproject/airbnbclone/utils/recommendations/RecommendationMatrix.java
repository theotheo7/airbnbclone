package com.schoolproject.airbnbclone.utils.recommendations;

import com.schoolproject.airbnbclone.models.SearchHistory;
import java.util.*;

public class RecommendationMatrix {

    private double[][] recommendationMatrix;
    private Map<Long, Integer> listings;
    private Map<Long, Integer> users;

    public RecommendationMatrix() {
        this.recommendationMatrix = null;
        this.listings = new HashMap<>();
        this.users = new HashMap<>();
    }

    public List<Long> recommend(Long id, Integer maximumRecommendations) {

        ArrayList<Long> recommendations = new ArrayList<>();

        /* In case the corresponding User was not considered during Factorization or there are no Listings available */
        if (this.recommendationMatrix == null || !this.users.containsKey(id) || this.listings.isEmpty())
            return recommendations;

        int userIndex = this.users.get(id);
        double[] userRecommendations = MatrixUtils.getRow(this.recommendationMatrix, userIndex);

        long[] listingIDS = new long[userRecommendations.length];
        for (Long listingID : this.listings.keySet())
            listingIDS[this.listings.get(listingID)] = listingID;

        List<UserRecommendation> userRecommendationsRecords = new ArrayList<>();
        for (int i = 0; i < listingIDS.length; i++)
            userRecommendationsRecords.add(new UserRecommendation(listingIDS[i], userRecommendations[i]));

        Collections.sort(userRecommendationsRecords);

        if (maximumRecommendations > userRecommendationsRecords.size())
            maximumRecommendations = userRecommendationsRecords.size();


        int index = userRecommendationsRecords.size() - 1;
        while (index >= 0 && maximumRecommendations != 0) {
            recommendations.add(userRecommendationsRecords.get(index).getListingID());
            maximumRecommendations--;
            index--;
        }

        return recommendations;
    }


    public void factorize(List<Long> sortedUsers, List<Long> sortedListings, List<SearchHistory> searchHistoryRecords) {

        if (sortedUsers.isEmpty() || sortedListings.isEmpty() || searchHistoryRecords.isEmpty())
            return;

        int totalUsers = sortedUsers.size();
        int totalListings = sortedListings.size();

        /* Create the following HashMap :
         *  # User - X <-> Index : 0
         *  # User - Y <-> Index : 1
         *  # Etc. */
        int userIndex = 0;
        HashMap<Long, Integer> userMap = new HashMap<>();
        for (Long user : sortedUsers) {
            userMap.put(user, userIndex);
            userIndex += 1;
        }

        /* Create the following HashMap :
         *  # Listing - X <-> Index : 0
         *  # Listing - Y <-> Index : 1
         *  # Etc. */
        int listingIndex = 0;
        HashMap<Long, Integer> listingMap = new HashMap<>();
        for (Long listing : sortedListings) {
            listingMap.put(listing, listingIndex);
            listingIndex += 1;
        }

        /* Create the User - Listing interactions Matrix and fill it with the appropriate values */
        double[][] matrix = new double[totalUsers][totalListings];
        for (SearchHistory record : searchHistoryRecords) {
            Long user = record.getId().getUserID();
            Long listing = record.getId().getListingID();
            double interactions = (double) record.getInteractions();
            matrix[userMap.get(user)][listingMap.get(listing)] = interactions;
        }


        MatrixUtils.normalise(matrix, totalUsers, totalListings);


        int latentFeatures = 3;
        double[][] V = MatrixUtils.randomMatrix(totalUsers, latentFeatures);
        double[][] F = MatrixUtils.randomMatrix(latentFeatures, totalListings);
        double previousValidationRMSE = Double.MAX_VALUE;
        int iterations = 0;
        int outOfPatience = 0;

        int epochs = 300;
        while (iterations != epochs){

            iterations++;

            /* Weights Update
             * The Validation set of a Matrix is the upper - left quarter of it while the rest of it
             * serves as the Training - Set */
            for (int i = 0; i < totalUsers; i++)
                for (int j = 0; j < totalListings; j++)
                    if (!isInValidationSet(i, j, totalUsers, totalListings) && matrix[i][j] != 0.0) {

                        double[] predictionRow = MatrixUtils.getRow(V, i);
                        double[] predictionColumn = MatrixUtils.getColumn(F, j);

                        double prediction = MatrixUtils.makePrediction(predictionRow, predictionColumn);
                        double eij = matrix[i][j] - prediction;

                        for (int k = 0; k < latentFeatures; k++) {

                            double learningRate = 0.001;
                            double regularizationPenalty = 0.09;
                            double vUpdate = V[i][k] + learningRate * (2.0 * eij * F[k][j] - regularizationPenalty * V[i][k]);
                            double fUpdate = F[k][j] + learningRate * (2.0 * eij * V[i][k] - regularizationPenalty * F[k][j]);

                            V[i][k] = vUpdate;
                            F[k][j] = fUpdate;
                        }


                    }

            /* Calculate RMSE in Validation - Set */
            double validationRMSE = 0.0;
            double validationSetCardinality = 0.0;

            for (int i = 0; i < totalUsers; i++)
                for (int j = 0; j < totalListings; j++){
                    if (isInValidationSet(i, j, totalUsers, totalListings) && matrix[i][j] != 0.0) {

                        double[] predictionRow = MatrixUtils.getRow(V, i);
                        double[] predictionColumn = MatrixUtils.getColumn(F, j);

                        double prediction = MatrixUtils.makePrediction(predictionRow, predictionColumn);
                        double eij = matrix[i][j] - prediction;

                        validationRMSE += Math.pow(eij, 2.0);
                        validationSetCardinality += 1.0;
                    }
                }


            /* Early Stopping */
            if (validationSetCardinality > 0.0 && validationRMSE > 0.0) {

                validationRMSE /= validationSetCardinality;
                validationRMSE = Math.sqrt(validationRMSE);


                if (validationRMSE < previousValidationRMSE && Math.abs(validationRMSE - previousValidationRMSE) > 0.00001) {
                    previousValidationRMSE = validationRMSE;
                    outOfPatience = 0;
                } else
                    outOfPatience += 1;

                int patience = 1;
                if (outOfPatience >= patience)
                    break;
            }


        }

        this.recommendationMatrix = MatrixUtils.multiplyMatrices(V, F, totalUsers, totalListings, latentFeatures);
        this.listings = listingMap;
        this.users = userMap;

    }

    /* The Validation set of a Matrix is the upper - left quarter of it */
    private static boolean isInValidationSet(int i, int j, int rows, int columns) {
        return (i < (rows / 2) && j < (columns / 2));
    }


}
