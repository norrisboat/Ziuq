//
//  LiveQuizView.swift
//  iosApp
//
//  Created by Norris Aboagye on 14/01/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared
import KMMViewModelSwiftUI

struct LiveQuizView: View {
    
    @ObservedViewModel var viewModel = LiveQuizViewModel()
    @State private var quizCode: String = ""
    @State private var showAlert: Bool = false
    
    @Binding var path: [NavPath]
    var categoryName: String
    var categoryKey: String
    var difficulty: QuizDifficulty
    
    var body: some View {
        ZStack {
            switch getState(screenState: viewModel.state) {
            case .idle:
                idle()
            case .loading, .creatingQuiz, .joiningQuiz, .loadingQuestions:
                statusMessage(quizState: getState(screenState: viewModel.state))
            case .quizCreated, .waitingForOpponent:
                VStack {
                    quizCreated()
                    waitingForOpponent()
                        .if(getState(screenState: viewModel.state) == .waitingForOpponent)
                }
                .padding()
            case .error(let errorMessage):
                ZiuqText(text: errorMessage, type: .label)
            }
        }
        .fillMaxSize()
        .background(Color.primaryGreen)
        .onFirstAppear {
            viewModel.observeNavigation()
            viewModel.setupQuiz(categoryKey: categoryKey, difficulty: difficulty.name)
        }
        .onChange(of: viewModel.quizUI) { quizUI in
            if let quiz = quizUI {
                path[path.count - 1] = .showQuiz(categoryName, quiz)
            }
        }
        .alert(Labels().quizCodeCopied.localized(), isPresented: $showAlert) {
            
        }
    }
}

extension LiveQuizView {
    
    @ViewBuilder
    func idle() -> some View {
        VStack {
            ZiuqTextField(text: $quizCode, placeholder: Labels().enterQuizCode.localized())
            PrimaryButton(title: Labels().joinQuiz.localized(), isDisabled: quizCode.isBlank) {
                viewModel.joinQuiz(quizCode: quizCode)
            }
            ZiuqText(text: "/", type: .title)
            
            CapsuleButton(title: Labels().createQuiz.localized()) {
                viewModel.createQuiz()
            }
        }
        .padding()
    }
    
    @ViewBuilder
    func quizCreated() -> some View {
        VStack {
            ZiuqText(text: Labels().quizCode.localized(), type: .heading, color: .deepGreen)
            ZiuqText(text: viewModel.liveQuizId, type: .subHeading)
            
            HStack {
                CapsuleButton(title: Labels().copyQuizCode.localized()) {
                    UIPasteboard.general.string = quizCode
                    showAlert = true
                }
                .fillWidth()
                
                Spacer(minLength: 24)
                
                PrimaryButton(title: Labels().share.localized()) {
                    let activityViewController = UIActivityViewController(activityItems: [quizCode], applicationActivities: nil)
                    UIApplication.shared.windows.first?.rootViewController?.present(activityViewController, animated: true, completion: nil)
                }
            }
            
        }
        .fillWidth()
    }
    
    @ViewBuilder
    func waitingForOpponent() -> some View {
        VStack {
            ProgressView()
            ZiuqText(text: Labels().waitingForOpponent.localized(), type: .mediumLabel)
        }
    }
    
    @ViewBuilder
    func statusMessage(quizState: LiveQuizState) -> some View {
        VStack {
            ProgressView()
            ZiuqText(text: statusTitle(quizState), type: .mediumLabel)
        }
    }
    
    func statusTitle(_ quizState: LiveQuizState) -> String {
        switch quizState {
        case .loading:
            Labels().loading.localized()
        case .creatingQuiz:
            Labels().creatingQuizMessage.localized()
        case .joiningQuiz:
            Labels().joiningQuiz.localized()
        case .loadingQuestions:
            Labels().loadingQuestions.localized()
        default :
            ""
        }
    }
    
    func getState(screenState: LiveQuizScreenState) -> LiveQuizState {
        if screenState as? LiveQuizScreenStateIdle != nil {
            return .idle
        } else if screenState as? LiveQuizScreenStateLoading != nil {
            return .loading
        } else if screenState as? LiveQuizScreenStateCreatingQuiz != nil {
            return .creatingQuiz
        } else if screenState as? LiveQuizScreenStateQuizCreated != nil {
            return .quizCreated
        } else if screenState as? LiveQuizScreenStateJoiningQuiz != nil {
            return .joiningQuiz
        } else if screenState as? LiveQuizScreenStateLoadingQuestions != nil {
            return .loadingQuestions
        } else if screenState as? LiveQuizScreenStateWaitingForOpponent != nil {
            return .waitingForOpponent
        }else if screenState as? LiveQuizScreenStateError != nil {
            return .error((screenState as? LiveQuizScreenStateError)?.errorMessage ?? "Something went wrong")
        }
        
        return .idle
    }
    
}

enum LiveQuizState : Equatable {
    case idle
    case loading
    case creatingQuiz
    case quizCreated
    case joiningQuiz
    case loadingQuestions
    case waitingForOpponent
    case error(String)
}

#Preview {
    LiveQuizView(path: .constant([]), categoryName: "", categoryKey: "", difficulty: QuizDifficulty.companion.sample)
}
