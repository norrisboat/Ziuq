//
//  QuizCompleteView.swift
//  iosApp
//
//  Created by Norris Aboagye on 09/08/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct QuizCompleteView: View {
    
    @Binding var path: [NavPath]
    var quizResult: QuizResult
    
    var body: some View {
        VStack {
            ZiuqText(text: resultTitle, type: .subHeading)
            ZiuqText(text: Labels().congratulationsMessage.localized(), type: .label)
                .multilineTextAlignment(.center)
            VStack {
                if quizResult.isLiveQuiz {
                    HStack {
                        ScoreTowerView(progress: CGFloat(quizResult.p1Score), max: 120, name: quizResult.p1Name, image: quizResult.p1Image, strokeColor: p1StrokeColor)
                        
                        Spacer(minLength: 8)
                        
                        ScoreTowerView(progress: CGFloat(quizResult.p2Score), max: 120, name: quizResult.p2Name, image: quizResult.p2Image, strokeColor: p2StrokeColor)
                    }
                } else {
                    ScoreTowerView(progress: CGFloat(quizResult.p1Score), max: 120, name: quizResult.p1Name, image: quizResult.p1Image)
                }
            }
            .frame(maxWidth: .infinity, maxHeight: .infinity)
            .padding(.bottom)
            
            PrimaryButton(title: Labels().done.localized()) {
                path = []
            }
            
        }
        .padding()
        .fillMaxSize()
        .background(Color.primaryGreen)
    }
}

extension QuizCompleteView {
    
    var resultTitle: String {
        if !self.quizResult.isLiveQuiz {
            Labels().congratulations.localized()
        } else if self.quizResult.p1Score == self.quizResult.p2Score {
            Labels().draw.localized()
        } else if self.quizResult.p1Score > self.quizResult.p2Score {
            Labels().youWin.localized()
        } else {
            Labels().youLost.localized()
        }
    }
    
    var p1StrokeColor: Color {
        if self.quizResult.p1Score == self.quizResult.p2Score {
            Color.gray
        } else if self.quizResult.p1Score > self.quizResult.p2Score {
            Color.green
        } else {
            Color.red
        }
    }
    
    var p2StrokeColor: Color {
        if p1StrokeColor == .gray {
            Color.gray
        } else if p1StrokeColor == .green {
            Color.red
        } else {
            Color.green
        }
    }
    
}

struct QuizCompleteView_Previews: PreviewProvider {
    static var previews: some View {
        QuizCompleteView(path: .constant([]), quizResult: QuizResult.companion.sample)
    }
}
