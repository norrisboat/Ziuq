//
//  CreatingQuizView.swift
//  iosApp
//
//  Created by Norris Aboagye on 07/08/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared
import KMMViewModelSwiftUI

struct CreatingQuizView: View {
    
    @StateViewModel private var viewModel = CreateQuizViewModel()
    @Binding var path: [NavPath]
    var categoryName: String
    var categoryKey: String
    var difficulty: String
    
    var body: some View {
        ZStack {
            switch getState(screenState: viewModel.state) {
            case .loading:
                VStack(spacing: 16) {
                    ProgressView()
                        .tint(.deepGreen)
                    ZiuqText(text: Labels().creatingQuiz.localized(), type: .mediumLabel, color: .deepGreen)
                }
                .fillMaxSize()
            case .success:
                EmptyView()
            case .error(let errorMessage):
                ZiuqText(text: errorMessage, type: .label, color: .deepGreen)
            }
            
        }
        .padding()
        .fillMaxSize()
        .background(Color.primaryGreen)
        .navigationTitle("")
        .onAppear {
            viewModel.createQuiz(
                createQuizRequest: CreateQuizRequest(
                    category: categoryKey,
                    difficulty: difficulty.lowercased(),
                    type: "text_choice"
                )
            )
        }
    }
}

enum CreatQuizViewState {
    case loading
    case success
    case error(String)
}

extension CreatingQuizView {
    
    func getState(screenState: CreateQuizScreenState) -> CreatQuizViewState {
        if screenState as? CreateQuizScreenStateLoading != nil {
            return .loading
        } else if screenState as? CreateQuizScreenStateSuccess != nil {
            if let lastPath = path.last, let screenState = screenState as? CreateQuizScreenStateSuccess, lastPath != .showQuiz(categoryName, screenState.quizUI) {
                path.append(.showQuiz(categoryName, screenState.quizUI))
            }
            return .success
        } else if screenState as? CreateQuizScreenStateError != nil {
            return .error((screenState as? CreateQuizScreenStateError)?.errorMessage ?? "Something went wrong")
        }
        
        return .loading
    }
    
}

struct CreatingQuizView_Previews: PreviewProvider {
    static var previews: some View {
        CreatingQuizView(path: .constant([]), categoryName: "Science", categoryKey: "science", difficulty: "easy")
    }
}
