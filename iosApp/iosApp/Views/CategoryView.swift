//
//  CategoryView.swift
//  iosApp
//
//  Created by Norris Aboagye on 04/08/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct CategoryView: View {
    
    var quizCategory: QuizCategory
    
    var body: some View {
        VStack {
            Image(resource: quizCategory.imageResource)
                .resizable()
                .renderingMode(.template)
                .foregroundColor(.deepGreen)
                .size(of: 70)
            
            ZiuqText(text: quizCategory.name, type: .custom(.spaceGrotesk, .subheadline, 20), color: .deepGreen)
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

struct CategoryView_Previews: PreviewProvider {
    static var previews: some View {
        CategoryView(quizCategory: QuizCategory.Companion().sample)
            .padding()
            .showAllPreviewTypes(previewLayout: .device)
    }
}
