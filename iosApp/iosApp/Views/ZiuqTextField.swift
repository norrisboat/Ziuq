//
//  ZiuqTextField.swift
//  iosApp
//
//  Created by Norris Aboagye on 02/08/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct ZiuqTextField: View {
    
    @Binding var text: String
    var placeholder: String = ""
    var textColor: Color = .label
    var isSecure: Bool = false
    
    @State private var isFilled: Bool = false
    
    var body: some View {
        ZStack(alignment: .leading) {
            ZiuqText(text: placeholder, type: isFilled ? .custom : .label)
                .foregroundColor(isFilled ? textColor : .gray.opacity(0.5))
                .frame(maxWidth: .infinity, alignment: .leading)
                .scaleEffect(isFilled ? 0.75 : 1, anchor: .topLeading)
                .offset(x: 0, y: isFilled ? -14 : 0)
                    
            if isSecure {
                SecureField("", text: $text)
                    .font(.urbanist(withStyle: .body))
                    .onChange(of: text) { _ in
                        if(text.isEmpty) {
                            withAnimation(.easeOut(duration: 0.15)) {
                                isFilled = false
                            }
                        } else {
                            withAnimation(.easeIn(duration: 0.15)) {
                                isFilled = true
                            }
                        }
                    }
            } else {
                TextField("", text: $text)
                    .font(.urbanist(withStyle: .body))
                    .onChange(of: text) { _ in
                        if(text.isEmpty) {
                            withAnimation(.easeOut(duration: 0.15)) {
                                isFilled = false
                            }
                        } else {
                            withAnimation(.easeIn(duration: 0.15)) {
                                isFilled = true
                            }
                        }
                    }
            }
        }
        .padding(20)
        .background(
            RoundedRectangle(cornerRadius: 70)
                .foregroundColor(Color.white.opacity(0.5))
        )
    }
}

struct ZiuqTextField_Previews: PreviewProvider {
    static var previews: some View {
        VStack {
            ZiuqTextField(text: .constant(""), placeholder: "Login")
            ZiuqTextField(text: .constant(""), placeholder: "Password", isSecure: true)
        }
        .fillMaxSize()
        .background(Color.purple)
    }
}
