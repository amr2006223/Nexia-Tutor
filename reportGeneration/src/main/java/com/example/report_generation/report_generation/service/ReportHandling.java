// package com.example.report_generation.report_generation.service;

// public class ReportHandling {
//     public static void getReport(int questionNumber, double userAccuracy, Object dataFilteredForUser) {
//         String[] problems = {
//                 "Which is Alphabetic Awareness, Phonological Awareness and Visual discrimination and categorization.",
//                 "Which is Phonological Awareness, Syllabic Awareness and Auditory Discrimination and Categorization",
//                 "Which is Lexical Awareness, Auditory Working Memory, and Auditory Discrimination and Categorization.",
//                 "Which is Visual Discrimination and Categorization, and Executive Functions",
//                 "Which is Visual Working Memory, Sequential Auditory Working Memory, and Auditory Discrimination and Categorization.",
//                 "Which is Lexical, Phonological, and Orthographic Awareness",
//                 "Which is Morphological and Semantic Awareness.",
//                 "Which is Syntactic Awareness",
//                 "Which is Phonological, Lexical and, Orthographic Awareness",
//                 "Which is Phonological, Lexical and Orthographic Awareness",
//                 "Which is Phonological, Lexical and Orthographic Awareness",
//                 "Which is Sequential Visual Working Memory ",
//                 "Which is Lexical, Orthographic Awareness and Auditory Working Memory, anama Sequential Auditory Working Memory and Phonological Awareness.",
//         };

//         if (questionNumber >= 1 && questionNumber <= 4) {
//             double average = getAverage(1, 4, dataFilteredForUser);
//             if (userAccuracy < average) {
//                 System.out.println("User has problem in Category 1 " + problems[0]);
//                 System.out.println("Accuracy of this category is " + average);
//                 System.out.println("User Accuracy is " + userAccuracy);
//             }
//             return;
//         } else if (questionNumber >= 5 && questionNumber <= 9) {
//             double average = getAverage(5, 9, dataFilteredForUser);
//             if (userAccuracy < average) {
//                 System.out.println("User has problem in Category 2 " + problems[1]);
//                 System.out.println("Accuracy of this category is " + average);
//                 System.out.println("User Accuracy is " + userAccuracy);
//             }
//             return;
//         } else if (questionNumber >= 10 && questionNumber <= 13) {
//             double average = getAverage(10, 13, dataFilteredForUser);
//             if (userAccuracy < average) {
//                 System.out.println("User has problem in Category 3 " + problems[2]);
//                 System.out.println("Accuracy of this category is " + average);
//                 System.out.println("User Accuracy is " + userAccuracy);
//             }
//             return;
//         } else if (questionNumber >= 14 && questionNumber <= 17) {
//             double average = getAverage(14, 17, dataFilteredForUser);
//             if (userAccuracy < average) {
//                 System.out.println("User has problem in Category 4 " + problems[3]);
//                 System.out.println("Accuracy of this category is " + average);
//                 System.out.println("User Accuracy is " + userAccuracy);
//             }
//             return;
//         } else if (questionNumber >= 18 && questionNumber <= 21) {
//             double average = getAverage(18, 21, dataFilteredForUser);
//             if (userAccuracy < average) {
//                 System.out.println("User has problem in Category 5 " + problems[4]);
//                 System.out.println("Accuracy of this category is " + average);
//                 System.out.println("User Accuracy is " + userAccuracy);
//             }
//             return;
//         } else if (questionNumber >= 22 && questionNumber <= 23) {
//             double average = getAverage(22, 23, dataFilteredForUser);
//             if (userAccuracy < average) {
//                 System.out.println("User has problem in Category 6 " + problems[5]);
//                 System.out.println("Accuracy of this category is " + average);
//                 System.out.println("User Accuracy is " + userAccuracy);
//             }
//             return;
//         } else if (questionNumber == 24) {
//             double average = getAverage(24, 24, dataFilteredForUser);
//             if (userAccuracy < average) {
//                 System.out.println("User has problem in Category 7 " + problems[6]);
//                 System.out.println("Accuracy of this category is " + average);
//                 System.out.println("User Accuracy is " + userAccuracy);
//             }
//             return;
//         } else if (questionNumber == 25) {
//             double categoryAverage = getAverage(25, 25, dataFilteredForUser);
//             if (userAccuracy < categoryAverage) {
//                 System.out.println("User has problem in Category 8 " + problems[7]);
//                 System.out.println("Accuracy of this category is " + categoryAverage);
//                 System.out.println("User Accuracy is " + userAccuracy);
//             }
//             return;
//         } else if (questionNumber == 26) {
//             double average = getAverage(26, 26, dataFilteredForUser);
//             if (userAccuracy < average) {
//                 System.out.println("User has problem in Category 9 " + problems[8]);
//                 System.out.println("Accuracy of this category is " + average);
//                 System.out.println("User Accuracy is " + userAccuracy);
//             }
//             return;
//         } else if (questionNumber >= 27 && questionNumber <= 28) {
//             double average = getAverage(27, 28, dataFilteredForUser);
//             if (userAccuracy < average) {
//                 System.out.println("User has problem in Category 10 " + problems[9]);
//                 System.out.println("Accuracy of this category is " + average);
//                 System.out.println("User Accuracy is " + userAccuracy);
//             }
//             return;
//         } else if (questionNumber == 29) {
//             double average = getAverage(29, 29, dataFilteredForUser);
//             if (userAccuracy < average) {
//                 System.out.println("User has problem in Category 11 " + problems[10]);
//                 System.out.println("Accuracy of this category is " + average);
//                 System.out.println("User Accuracy is " + userAccuracy);
//             }
//             return;
//         } else if (questionNumber == 30) {
//             double average = getAverage(30, 30, dataFilteredForUser);
//             if (userAccuracy < average) {
//                 System.out.println("User has problem in Category 12 " + problems[11]);
//                 System.out.println("Accuracy of this category is " + average);
//                 System.out.println("User Accuracy is " + userAccuracy);
//             }
//             return;
//         } else if (questionNumber >= 31 && questionNumber <= 32) {
//             double average = getAverage(31, 32, dataFilteredForUser);
//             if (userAccuracy < average) {
//                 System.out.println("User has problem in Category 13 " + problems[12]);
//                 System.out.println("Accuracy of this category is " + average);
//                 System.out.println("User Accuracy is " + userAccuracy);
//             }
//             return;
//         }
//     }

//     // Define the getAverage method here
//     public static double getAverage(int start, int end, Object dataFilteredForUser) {
//         // Implement your logic to calculate average here
//         return 0.0; // Replace this with your calculated average
//     }

// }
