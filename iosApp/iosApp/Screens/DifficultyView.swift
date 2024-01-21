//
//  DifficultyView.swift
//  iosApp
//
//  Created by Norris Aboagye on 05/08/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared
import KMMViewModelSwiftUI

struct DifficultyView: View {
    
    @ObservedViewModel private var viewModel = DifficultyViewModel()
    @Binding var path: [NavPath]
    var categoryName: String
    var categoryKey: String
    
    private let twoColumnGrid = [GridItem(.flexible()), GridItem(.flexible())]
    
    var body: some View {
            VStack {
                switch getState(screenState: viewModel.state) {
                case .idle:
                    Spacer()
                case .loading:
                    ZStack {
                        ProgressView()
                    }
                    .fillMaxSize()
                case .success(let difficulties):
                    ScrollView {
                        GeometryReader { proxy in
                            LazyVGrid(columns: twoColumnGrid) {
                                ForEach((difficulties), id: \.key) { difficulty in
                                    DifficultyItemView(quizDifficulty: difficulty)
                                        .frame(width: proxy.size.width / 2 - 8)
                                        .frame(minHeight: 220)
                                        .padding(.bottom)
                                        .makeButton {
                                            path.append(.chooseQuiz(categoryName, categoryKey, difficulty))
                                        }
                                }
                            }
                        }
                    }
                    .fillMaxSize()
                case .error(let errorMessage):
                    ZiuqText(text: errorMessage, type: .label, color: .deepGreen)
                }
            }
            .padding()
            .fillMaxSize(alignment: .top)
            .background(Color.primaryGreen)
            .navigationTitle(categoryName)
    }
}
enum DifficultyViewState {
    case idle
    case loading
    case success([QuizDifficulty])
    case error(String)
}

extension DifficultyView {
    
    func getState(screenState: DifficultyScreenState) -> DifficultyViewState {
        if screenState as? DifficultyScreenStateIdle != nil {
            return .idle
        } else if screenState as? DifficultyScreenStateLoading != nil {
            return .loading
        } else if screenState as? DifficultyScreenStateSuccess != nil {
            var difficulties: [QuizDifficulty] = []
            if let quizDifficulties = (screenState as? DifficultyScreenStateSuccess)?.difficulties {
                difficulties.append(contentsOf: quizDifficulties)
            }
            return .success(difficulties)
        } else if screenState as? DifficultyScreenStateError != nil {
            return .error((screenState as? DifficultyScreenStateError)?.errorMessage ?? "Something went wrong")
        }
        
        return .idle
    }
    
}

struct DifficultyView_Previews: PreviewProvider {
    static var previews: some View {
        DifficultyView(path: .constant([]), categoryName: "Science", categoryKey: "science")
    }
}
