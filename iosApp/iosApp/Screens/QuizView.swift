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
            verticalProgress()
            VStack {
                if quizUI.isLiveQuiz() {
                    LiveScoreView(p1Image: ExtensionsKt.toUserImage(quizUI.player1?.image ?? "") , p1Name: quizUI.player1?.name ?? "", p1Score: Int(viewModel.currentScore), p2Image: ExtensionsKt.toUserImage(quizUI.player2?.image ?? ""), p2Name: quizUI.player2?.name ?? "", p2Score: Int(viewModel.opponentScore))
                        .padding(.horizontal)
                }
                
                if let quizQuestion = currentQuestion {
                    QuizQuestionView(questionNumber: Int(viewModel.questionNumber), quizQuestion: quizQuestion, opponentAnswer: viewModel.opponentAnswer, opponentImage: ExtensionsKt.toUserImage(quizUI.player2?.image ?? ""), isPlayer1: quizUI.player1?.username == viewModel.getUserId()) { answer in
                        if quizUI.isLiveQuiz() {
                            viewModel.submitAnswer(answer: answer)
                        } else {
                            viewModel.nextQuestion(answer: answer, index: nil)
                        }
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
                    if quizUI.isLiveQuiz() {
                        viewModel.submitAnswer(answer: "")
                    } else {
                        viewModel.nextQuestion(answer: "", index: nil)
                    }
                }
                .padding()
                
            }
        }
        .navigationTitle("")
        .toolbar {
            ToolbarItem(placement: .principal) {
                quizToolbar()
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
                path.append(.showQuizCompleted(viewModel.quizResult(quizUI: quizUI)))
            }
        }
    }
}

extension QuizView {
    @ViewBuilder
    func verticalProgress() -> some View {
        HStack {
            VerticalProgressView(progress: timeLeft, max: 15)
            Spacer()
            VerticalProgressView(progress: timeLeft, max: 15)
        }
    }
    
    @ViewBuilder
    func quizToolbar() -> some View {
        HStack {
            Spacer()
            VStack {
                ZiuqText(text: Labels().categoryQuiz(category: category).localized(), type: .label)
                ZiuqText(text: Labels().numberQuestion(number: Int32(quizUI.questions.count)).localized(), type: .smallLabel)
            }
            Spacer()
            if !quizUI.isLiveQuiz() {
                QuizScoreView(score: Int(viewModel.currentScore))
                    .padding(.trailing)
            }
        }
        .fillWidth()
    }
}

struct QuizView_Previews: PreviewProvider {
    static var previews: some View {
        QuizView(path: .constant([]), category: "Sceince", quizUI: QuizUI.Companion().sample)
    }
}
