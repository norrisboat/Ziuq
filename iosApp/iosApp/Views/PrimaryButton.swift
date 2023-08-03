//
//  PrimaryButton.swift
//  iosApp
//
//  Created by Norris Aboagye on 02/08/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct PrimaryButton: View {
    
    @State private var isPressed = false
    
    let title: String
    let isDisabled: Bool
    var faintBackground: Bool = false
    var accentColor: Color = .deepGreen
    let action: () -> Void
    
    init(title: String, action: @escaping () -> Void) {
        self.title = title
        self.action = action
        self.isDisabled = false
    }
    
    init(title: String, isDisabled: Bool, action: @escaping () -> Void) {
        self.title = title
        self.action = action
        self.isDisabled = isDisabled
    }
    
    init(title: String, faintBackground: Bool, action: @escaping () -> Void) {
        self.title = title
        self.action = action
        self.faintBackground = faintBackground
        self.isDisabled = false
    }
    
    init(title: String, faintBackground: Bool, accentColor: Color, isDisabled: Bool, action: @escaping () -> Void) {
        self.title = title
        self.action = action
        self.faintBackground = faintBackground
        self.accentColor = accentColor
        self.isDisabled = isDisabled
    }
    
    var body: some View {
        Button(
            action: {
                withAnimation(.spring()) {
                    if !isDisabled {
                        delayScale()
                        isPressed = true
                        action()
                    }
                }
            },
            label: {
                Text(title).foregroundColor(faintBackground && !isDisabled ? accentColor : Color.white.opacity(isDisabled ? 0.8 : 1))
                    .font(.spaceGrotesk())
                    .fillWidth()
                    .frame(height: 48.0)
            }
        )
        .fillWidth()
        .background(Capsule().fill(isDisabled ? .gray : faintBackground ? accentColor.opacity(0.2) : accentColor))
            .animation(.default, value: isDisabled)
            .padding()
            .scaleEffect(isPressed ? 0.95 : 1.0)
            .scaleEffect(isDisabled ? 0.95 : 1)
            .animation(.interpolatingSpring(stiffness: 175, damping: 10), value: isDisabled)
    }
    
    private func delayScale() {
        DispatchQueue.main.asyncAfter(deadline: .now() + 0.15) {
            isPressed = false
        }
    }
}

struct PrimaryButton_Previews: PreviewProvider {
    static var previews: some View {
        VStack {
            PrimaryButton(title: "Sample Text") {}
            PrimaryButton(title: "Sample Text", isDisabled: true) {}
            PrimaryButton(title: "Sample Text", faintBackground: true) {}
        }
        
    }
}
