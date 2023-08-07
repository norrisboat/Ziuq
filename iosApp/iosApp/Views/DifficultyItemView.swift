//
//  DifficultyItemView.swift
//  iosApp
//
//  Created by Norris Aboagye on 05/08/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct DifficultyItemView: View {
    
    var quizDifficulty: QuizDifficulty
    
    var body: some View {
        VStack {
            Image(resource: quizDifficulty.imageResource)
                .resizable()
                .renderingMode(.template)
                .foregroundColor(.deepGreen)
                .size(of: 70)
            
            ZiuqText(text: quizDifficulty.name, type: .custom(.spaceGrotesk, .subheadline, 20), color: .deepGreen)
                .multilineTextAlignment(.center)
                .sidePadding()
        }
        .fillMaxSize()
        .background(Color.lightYellow)
        .cornerRadius(40)
        .overlay(
            RoundedRectangle(cornerRadius: 40)
                .strokeBorder(.white, lineWidth: 1)
        )
        
    }
}

struct DifficultyItemView_Previews: PreviewProvider {
    static var previews: some View {
        DifficultyItemView(quizDifficulty: QuizDifficulty.Companion().sample)
    }
}
