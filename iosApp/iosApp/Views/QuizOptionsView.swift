//
//  QuizOptionsView.swift
//  iosApp
//
//  Created by Norris Aboagye on 07/08/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct QuizOptionsView: View {
    
    var options: [String]
    var correctAnswer: String
    let action: (String) -> Void
    
    @State var animateResults: Bool = false
    @State var correct: String = ""
    @State var wrong: String = ""
    @State var selectedOption: String = ""
    
    var body: some View {
        VStack(spacing: 16) {
            ForEach(options, id: \.self) { option in
                CapsuleButton(
                    title: option,
                    backgroundColor: backgroundColor(option),
                    textColor: textColor(option),
                    borderColor: borderColor(option),
                    canScale: false
                ) {
                    withAnimation(.easeIn) {
                        selectedOption = option
                        if (option == correctAnswer) {
                            correct = option
                        } else {
                            wrong = option
                            correct = correctAnswer
                        }
                        animateResults = true
                    }
                    
                }
            }
        }
        .onChange(of: animateResults) { animate in
            DispatchQueue.main.asyncAfter(deadline: .now() + 1.5) {
                if animate {
                    action(selectedOption)
                    DispatchQueue.main.asyncAfter(deadline: .now() + 0.5) {
                        withAnimation(.easeOut) {
                            selectedOption = ""
                            correct = ""
                            wrong = ""
                            selectedOption = ""
                            animateResults = false
                        }
                    }
                }
            }
        }
    }
    
}

extension QuizOptionsView {
    func backgroundColor(_ option: String) -> Color {
        if option == correct {
            return .green
        } else if option == wrong {
            return .red
        } else {
            return .white
        }
    }
    
    func textColor(_ option: String) -> Color {
        if option == wrong {
            return .white
        } else {
            return .deepGreen
        }
    }
    
    func borderColor(_ option: String) -> Color {
        if option == correct {
            return backgroundColor(option)
        } else {
            return .gray.opacity(0.5)
        }
    }
}

struct QuizOptionsView_Previews: PreviewProvider {
    static var previews: some View {
        QuizOptionsView(options: ["Person", "Object", "Town", "Animal"], correctAnswer: "Person") { answer in
            
        }
        .padding()
    }
}
