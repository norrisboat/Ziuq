//
//  QuizView.swift
//  iosApp
//
//  Created by Norris Aboagye on 07/08/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared
import KMMViewModelSwiftUI

struct QuizView: View {
    
    @StateViewModel private var viewModel = QuizViewModel()
    @State private var currentQuestion: QuizQuestion? = nil
    @State private var timeLeft: CGFloat = 30
    
    @Binding var path: [NavPath]
    var category: String
    var quizUI: QuizUI
    
    var body: some View {
        
        ZStack(alignment: .top) {
            HStack {
                VerticalProgressView(progress: timeLeft, max: 30)
                Spacer()
                VerticalProgressView(progress: timeLeft, max: 30)
            }
            VStack {
                if let quizQuestion = currentQuestion {
                    QuizQuestionView(questionNumber: Int(viewModel.questionNumber), quizQuestion: quizQuestion) { answer in
                        viewModel.nextQuestion(answer: answer)
                    }
                    .sidePadding(padding: 28)
                    .topAndDownPadding()
                    .transition(.asymmetric(insertion: .move(edge: .trailing), removal: .move(edge: .leading)))
                    .id(quizQuestion)
                    
                } else {
                    ProgressView()
                        .padding(.top, 56)
                }
                
                Spacer()
                
                CapsuleButton(title: Labels().skip.localized()) {
                    viewModel.nextQuestion(answer: "")
                }
                .padding()
                
            }
        }
        .navigationTitle("")
        .toolbar {
            ToolbarItem(placement: .principal) {
                HStack {
                    Spacer()
                    VStack {
                        ZiuqText(text: Labels().categoryQuiz(category: category).localized(), type: .label)
                        ZiuqText(text: Labels().numberQuestion(number: Int32(quizUI.questions.count)).localized(), type: .smallLabel)
                    }
                    Spacer()
                    QuizScoreView(score: Int(viewModel.currentScore))
                        .padding(.trailing)
                }
                .fillWidth()
            }
        }
        .fillMaxSize(alignment: .top)
        .onAppear {
            viewModel.setQuizUI(quizUI: quizUI)
        }
        .onChange(of: viewModel.currentQuestion) { question in
            if let question = question {
                withAnimation {
                    currentQuestion = question
                }
            }
        }
        .onChange(of: viewModel.timeLeft) { time in
            withAnimation {
                timeLeft = CGFloat(time)
            }
        }
        .onChange(of: viewModel.isCompleted) { isCompleted in
            if isCompleted {
                path.append(.showQuizCompleted)
            }
        }
    }
}

struct QuizView_Previews: PreviewProvider {
    static var previews: some View {
        QuizView(path: .constant([]), category: "Sceince", quizUI: QuizUI.Companion().sample)
    }
}
