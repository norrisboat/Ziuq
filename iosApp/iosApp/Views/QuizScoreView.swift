//
//  QuizScoreView.swift
//  iosApp
//
//  Created by Norris Aboagye on 09/08/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct QuizScoreView: View {
    var score: Int
    var showCircle: Bool = true
    var textSize: CGFloat = 14
    
    @State private var animatedScore: Int = 0
    
    var body: some View {
        ZStack {
            Text("\(animatedScore)")
                .font(.spaceGrotesk(size: textSize))
                .transition(.asymmetric(insertion: .move(edge: .bottom), removal: .move(edge: .top)).combined(with: .opacity))
                .id(animatedScore)
            if showCircle {
                Circle()
                    .strokeBorder(.gray.opacity(0.5))
                    .size(of: 35)
            }
        }
        .onChange(of: score) { currentScore in
            withAnimation {
                animatedScore = currentScore
            }
        }
    }
}

struct QuizScoreView_Previews: PreviewProvider {
    static var previews: some View {
        QuizScoreView(score: 0)
    }
}
