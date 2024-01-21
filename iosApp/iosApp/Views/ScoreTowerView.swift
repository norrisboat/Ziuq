//
//  ScoreTowerView.swift
//  iosApp
//
//  Created by Norris Aboagye on 16/01/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct ScoreTowerView: View {
    
    var progress: CGFloat
    var max: CGFloat
    var name: String
    var image: String
    var strokeColor: Color = .clear
    @State var animProgress: CGFloat = 0
    @State var showImage = false
    
    var body: some View {
        GeometryReader { proxy in
            VStack {
                Rectangle()
                    .opacity(0)
                    .frame(maxHeight: proxy.size.height  - (proxy.size.height * (animProgress / max)), alignment: .bottom)
                
                ZStack(alignment: .top) {
                    Rectangle()
                        .fill(
                            LinearGradient(gradient: Gradient(colors: [.white.opacity(0.8), .clear]), startPoint: .top, endPoint: .bottom)
                        )
                    
                    Rectangle()
                        .size(of: proxy.size.width - 47)
                        .foregroundStyle(Color.deepGreen)
                        .rotationEffect(.degrees(45))
                        .offset(y: -57)
                    
                    if let imageURL = image.toUrl {
                        AvatarView(url: imageURL, size: 70)
                            .overlay {
                                Circle().stroke(strokeColor, lineWidth: 2)
                            }
                            .offset(y: -100)
                            .if(showImage)
                    }
                    
                    ZiuqText(text: name, type: .label, color: .deepGreen)
                        .offset(y: 90)
                    
                    ZiuqText(text: "\(Int(progress))", type: .subHeading, color: .deepGreen)
                        .offset(y: 115)
                }
                .frame(maxHeight: proxy.size.height * (animProgress / max), alignment: .bottom)
            }
        }
        .frame(width: 160)
        .fillHeight()
        .onFirstAppear {
            withAnimation(.interpolatingSpring(stiffness: 175, damping: 16).delay(1)) {
                animProgress = progress
                wait(for: 1.2) {
                    showImage = true
                }
            }
        }
    }
}

#Preview {
    ZStack {
        HStack {
            ScoreTowerView(progress: 40, max: 120, name: "Player 1", image: "https://global.discourse-cdn.com/monzo/original/3X/8/6/866e6d84e8c756b19050fbe2ca0932858118614c.jpg")
            ScoreTowerView(progress: 100, max: 120, name: "Player 1", image: "https://global.discourse-cdn.com/monzo/original/3X/8/6/866e6d84e8c756b19050fbe2ca0932858118614c.jpg")
        }
    }
    .fillMaxSize()
    .background(Color.primaryGreen)
}
