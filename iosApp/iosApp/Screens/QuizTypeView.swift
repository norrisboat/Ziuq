//
//  QuizTypeView.swift
//  iosApp
//
//  Created by Norris Aboagye on 11/01/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

struct QuizTypeView: View {
    
    private let twoColumnGrid = [GridItem(.flexible()), GridItem(.flexible())]
    private let quizTypes = [QuizType.companion.multiplayer,QuizType.companion.startQuiz]
    
    @Binding var path: [NavPath]
    var categoryName: String
    var categoryKey: String
    var difficulty: QuizDifficulty
    
    var body: some View {
        ScrollView {
            GeometryReader { proxy in
                LazyVGrid(columns: twoColumnGrid) {
                    ForEach(quizTypes, id: \.imageResource) { quizType in
                        QuizTypeItemView(quizType: quizType)
                            .frame(width: proxy.size.width / 2 - 24)
                            .frame(minHeight: 220)
                            .padding(.bottom)
                            .makeButton {
                                if quizType == QuizType.companion.startQuiz  {
                                    path.append(.setupQuiz(categoryName, categoryKey, difficulty))
                                } else {
                                    path.append(.liveQuiz(categoryName, categoryKey, difficulty))
                                }
                            }
                    }
                }
                .padding()
            }
        }
        .fillMaxSize()
        .background(Color.primaryGreen)
    }
}

#Preview {
    QuizTypeView(path: .constant([]), categoryName: "", categoryKey: "", difficulty: QuizDifficulty.companion.sample)
}
