//
//  QuizQuestionView.swift
//  iosApp
//
//  Created by Norris Aboagye on 07/08/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct QuizQuestionView: View {
    
    var questionNumber: Int
    var quizQuestion: QuizQuestion
    let onAnswer: (String) -> Void
    
    var body: some View {
        VStack(alignment: .leading, spacing: 10) {
            
                ZiuqText(text: Labels().questionNumber(number: Int32(questionNumber)).localized(), type: .custom(.spaceGrotesk, .body, 16))
                ZiuqText(text: quizQuestion.question, type: .custom(.spaceGrotesk, .title, 18), color: .deepGreen)
                
                QuizOptionsView(options: quizQuestion.options, correctAnswer: quizQuestion.correctAnswer) { answer in
                    onAnswer(answer)
                }
                .padding(.top, 24)
                .sidePadding(padding: 16)
        }
        .padding()
        .fillWidth(alignment: .leading)
        .background {
            RoundedRectangle(cornerRadius: 16).fill(Color.systemBackgroundColor)
                .shadow(color: Color.label.opacity(0.4), radius: 1)
        }
    }
}

struct QuizQuestionView_Previews: PreviewProvider {
    static var previews: some View {
        QuizQuestionView(questionNumber: 1, quizQuestion: QuizQuestion.Companion().sample) { ans in }
            .padding()
    }
}
