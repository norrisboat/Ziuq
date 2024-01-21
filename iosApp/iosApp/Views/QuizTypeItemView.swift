//
//  QuizTypeItemView.swift
//  iosApp
//
//  Created by Norris Aboagye on 11/01/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

struct QuizTypeItemView: View {
	
    var quizType: QuizType
    
    var body: some View {
        VStack {
            Image(resource: quizType.imageResource)
                .resizable()
                .renderingMode(.template)
                .foregroundColor(.deepGreen)
                .size(of: 70)
            
            ZiuqText(text: quizType.name.localized(), type: .custom(.spaceGrotesk, .subheadline, 20), color: .deepGreen)
                .multilineTextAlignment(.center)
                .sidePadding()
        }
        .fillMaxSize()
        .frame(height: 240)
        .background(Color.lightYellow)
        .cornerRadius(40)
        .overlay(
            RoundedRectangle(cornerRadius: 40)
                .strokeBorder(.white, lineWidth: 1)
        )
    }
}

#Preview {
    QuizTypeItemView(quizType: QuizType.companion.startQuiz)
}
