//
//  HomeView.swift
//  iosApp
//
//  Created by Norris Aboagye on 03/08/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared
import KMMViewModelSwiftUI

struct HomeView: View {
    
    @ObservedViewModel var viewModel = HomeViewModel()
    private var twoColumnGrid = [GridItem(.flexible()), GridItem(.flexible())]
    @State private var path: [NavPath] = []
    
    
    var body: some View {
        NavigationStack(path: $path) {
            ZStack {
                VStack {
                    ZiuqText(text: Labels().hello.localized(), type: .smallLabel)
                        .fillWidth(alignment: .leading)
                    ZiuqText(text: Labels().dearUser.localized(), type: .subTitle)
                        .fillWidth(alignment: .leading)
                    
                    switch getState(screenState: viewModel.state) {
                    case .idle:
                        Spacer()
                    case .loading:
                        ZStack {
                            ProgressView()
                        }
                        .fillMaxSize()
                    case .success(let categories):
                        ScrollView {
                            GeometryReader { proxy in
                                LazyVGrid(columns: twoColumnGrid) {
                                    ForEach((categories), id: \.key) { category in
                                        CategoryView(quizCategory: category)
                                            .frame(width: proxy.size.width / 2 - 8)
                                            .frame(minHeight: 220)
                                            .padding(.bottom)
                                            .makeButton {
                                                path.append(.difficulty(category))
                                            }
                                    }
                                }
                            }
                        }
                        .fillMaxSize()
                        .navigationDestination(for: NavPath.self) { path in
                            switch path {
                            case .difficulty(let category):
                                DifficultyView(path: $path, categoryName: category.name, categoryKey: category.key)
                            case .setupQuiz(let categoryName, let categoryKey, let difficulty):
                                CreatingQuizView(path: $path, categoryName: categoryName, categoryKey: categoryKey, difficulty: difficulty.name)
                            case .showQuiz(let category, let quizUI):
                                QuizView(path: $path, category: category, quizUI: quizUI)
                                    .onFirstAppear {
//                                        self.path = []
                                    }
                            case .showQuizCompleted:
                                QuizCompleteView(path: $path)
                            }
                        }
                    case .error(let errorMessage):
                        ZiuqText(text: errorMessage, type: .label, color: .deepGreen)
                    }
                }
                .padding(.top, 36)
                .padding(.leading, 16)
            }
            .padding()
            .fillMaxSize(alignment: .top)
            .background(Color.primaryGreen)
            .ignoresSafeArea()
        }
        .tint(.label)
    }
}

enum HomeViewState {
    case idle
    case loading
    case success([QuizCategory])
    case error(String)
}

enum NavPath: Hashable {
    case difficulty(QuizCategory)
    case setupQuiz(String, String, QuizDifficulty)
    case showQuiz(String, QuizUI)
    case showQuizCompleted
}

extension HomeView {
    
    func getState(screenState: HomeScreenState) -> HomeViewState {
        if screenState as? HomeScreenStateIdle != nil {
            return .idle
        } else if screenState as? HomeScreenStateLoading != nil {
            return .loading
        } else if screenState as? HomeScreenStateSuccess != nil {
            var categories: [QuizCategory] = []
            if let quizCategories = (screenState as? HomeScreenStateSuccess)?.categories {
                categories.append(contentsOf: quizCategories)
            }
            return .success(categories)
        } else if screenState as? HomeScreenStateError != nil {
            return .error((screenState as? HomeScreenStateError)?.errorMessage ?? "Something went wrong")
        }
        
        return .idle
    }
    
}

struct HomeView_Previews: PreviewProvider {
    static var previews: some View {
        HomeView()
    }
}
