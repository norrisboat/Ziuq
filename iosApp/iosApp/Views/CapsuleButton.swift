//
//  CapsuleButton.swift
//  iosApp
//
//  Created by Norris Aboagye on 07/08/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct CapsuleButton: View {
    
    @State var isPressed = false
    
    let title: String
    let action: () -> Void
    var tint: Color = .white
    var textColor: Color = .deepGreen
    var borderColor: Color = .gray
    var canScale: Bool = true
    
    init(title: String, action: @escaping () -> Void) {
        self.title = title
        self.action = action
    }
    
    init(title: String, tint: Color, action: @escaping () -> Void) {
        self.title = title
        self.action = action
        self.tint = tint
    }
    
    init(title: String, tint: Color,textColor: Color, action: @escaping () -> Void) {
        self.title = title
        self.action = action
        self.tint = tint
        self.textColor = textColor
    }
    
    init(title: String, backgroundColor: Color,textColor: Color, borderColor: Color, canScale: Bool, action: @escaping () -> Void) {
        self.title = title
        self.action = action
        self.tint = backgroundColor
        self.borderColor = borderColor
        self.textColor = textColor
        self.canScale = canScale
    }
    
    var body: some View {
        Button(action: {
            withAnimation(.spring()) {
                if canScale {
                    delayScale()
                }
                isPressed = true
                action()
            }
        }, label: {
            Text(title)
                .foregroundColor(textColor)
                .font(.spaceGrotesk())
                .sidePadding()
                .frame(maxWidth: .infinity, maxHeight: 24.0)
        })
        .topAndDownPadding(padding: 8)
        .fillWidth()
        .frame(height: 45)
        .background {
            Capsule()
                .strokeBorder(borderColor, lineWidth: 0.5)
                .background(tint)
                .clipped()
        }
        .clipShape(Capsule())
        .scaleEffect(isPressed && canScale ? 0.95 : 1.0)
    }
    
    private func delayScale() {
        DispatchQueue.main.asyncAfter(deadline: .now() + 0.15) {
            isPressed = false
        }
    }
}

struct CapsuleButton_Previews: PreviewProvider {
    static var previews: some View {
        CapsuleButton(title: "Option 1", action: {})
    }
}
