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
    @ObservedObject var sessionViewModel = SessionViewModel.shared
    private var twoColumnGrid = [GridItem(.flexible()), GridItem(.flexible())]
    @State private var path: [NavPath] = []
    @State var showSheet = false
    
    var body: some View {
        NavigationStack(path: $path) {
            ZStack {
                VStack {
                    HStack {
                        VStack {
                            
                            ZiuqText(text: Labels().hello.localized(), type: .subTitle)
                                .fillWidth(alignment: .leading)
                            ZiuqText(text: viewModel.getUsername(), type: .title)
                                .fillWidth(alignment: .leading)
                        }
                        Spacer()
                        Image(resource: Images().menu)
                            .resizable()
                            .renderingMode(.template)
                            .foregroundColor(.deepGreen)
                            .size(of: 24)
                            .makeButton {
                                showSheet = true
                            }
                    }
                    
                    switch getState(screenState: viewModel.state) {
                    case .idle:
                        Spacer()
                    case .loading:
                        ZStack {
                            ProgressView()
                        }
                        .fillMaxSize()
                    case .success(let categories):
                        GeometryReader { proxy in
                            ScrollView(showsIndicators: false) {
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
                    case .error(let errorMessage):
                        ZiuqText(text: errorMessage, type: .label, color: .deepGreen)
                    }
                }
                .padding(.top, 36)
                .padding(.horizontal, 8)
            }
            .padding()
            .fillMaxSize(alignment: .top)
            .background(Color.primaryGreen)
            .ignoresSafeArea()
            .navigationDestination(for: NavPath.self) { path in
                switch path {
                case .difficulty(let category):
                    DifficultyView(path: $path, categoryName: category.name, categoryKey: category.key)
                case .setupQuiz(let categoryName, let categoryKey, let difficulty):
                    CreatingQuizView(path: $path, categoryName: categoryName, categoryKey: categoryKey, difficulty: difficulty.name)
                case .showQuiz(let category, let quizUI):
                    QuizView(path: $path, category: category, quizUI: quizUI)
                case .showQuizCompleted(let quizResult):
                    QuizCompleteView(path: $path, quizResult: quizResult)
                case .chooseQuiz(let categoryName, let categoryKey, let difficulty):
                    QuizTypeView(path: $path, categoryName: categoryName, categoryKey: categoryKey, difficulty: difficulty)
                case .liveQuiz(let categoryName, let categoryKey, let difficulty):
                    LiveQuizView(path: $path, categoryName: categoryName, categoryKey: categoryKey, difficulty: difficulty)
                }
            }
            .sheet(isPresented: $showSheet) {
                VStack {
                    ZiuqText(text: Labels().more.localized(), type: .heading, color: .deepGreen)
                        .fillWidth(alignment: .leading)
                        .padding()
                    PrimaryButton(title: Labels().joinQuiz.localized()) {
                        withAnimation {
                            showSheet = false
                        }
                        path.append(.liveQuiz("", "", QuizDifficulty.companion.sample))
                    }
                    CapsuleButton(title: Labels().logout.localized()) {
                        withAnimation {
                            showSheet = false
                            sessionViewModel.logout()
                        }
                    }
                    .padding(.bottom)
                    .padding(.horizontal)
                }
                .fillWidth(alignment: .center)
                .frame(height: 250)
                .background(Color.primaryGreen)
                .presentationDetents([.height(250)])
            }
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
    case chooseQuiz(String, String, QuizDifficulty)
    case setupQuiz(String, String, QuizDifficulty)
    case liveQuiz(String, String, QuizDifficulty)
    case showQuiz(String, QuizUI)
    case showQuizCompleted(QuizResult)
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
