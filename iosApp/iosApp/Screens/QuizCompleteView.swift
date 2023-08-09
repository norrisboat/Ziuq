//
//  QuizCompleteView.swift
//  iosApp
//
//  Created by Norris Aboagye on 09/08/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct QuizCompleteView: View {
    
    @Binding var path: [NavPath]
    
    var body: some View {
        VStack {
            Spacer()
            ZiuqText(text: Labels().congratulations.localized(), type: .subHeading)
            ZiuqText(text: Labels().congratulationsMessage.localized(), type: .subTitle)
            Spacer()
            
            PrimaryButton(title: Labels().done.localized()) {
                path = []
            }
            
        }
        .padding()
        .fillMaxSize()
        .background(Color.primaryGreen)
    }
}

struct QuizCompleteView_Previews: PreviewProvider {
    static var previews: some View {
        QuizCompleteView(path: .constant([]))
    }
}
