//
//  LiveScoreView.swift
//  iosApp
//
//  Created by Norris Aboagye on 11/01/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct LiveScoreView: View {
    
    var p1Image: String
    var p1Name: String
    var p1Score: Int
    var p2Image: String
    var p2Name: String
    var p2Score: Int
    
    var body: some View {
        ZStack(alignment: .center) {
            HStack{
                player1View()
                player2View()
            }.padding()
            
            ZiuqText(text: ":", type: .title)
        }
        .fillWidth()
        .overlay(
            RoundedRectangle(cornerRadius: 16)
                .strokeBorder(Color.primaryGreen, lineWidth: 1)
        )
        .makeCard()
        .padding()
    }
}

extension LiveScoreView {
    @ViewBuilder 
    func player1View() -> some View {
        HStack(alignment: .center) {
            VStack {
                if let p1Image = p1Image.toUrl {
                    AvatarView(url: p1Image, size: 50)
                }
                
                ZiuqText(text: p1Name, type: .smallLabel)
                    .fontWeight(.semibold)
                    .lineLimit(1)
            }
            
            Spacer()
            
            QuizScoreView(score: p1Score, showCircle: false, textSize: 32)
        }
        .padding(.trailing, 4)
    }
    
    @ViewBuilder
    func player2View() -> some View {
        HStack(alignment: .center) {
            QuizScoreView(score: p2Score, showCircle: false, textSize: 32)
            
            Spacer()
            
            VStack {
                if let p2Image = p2Image.toUrl {
                    AvatarView(url: p2Image, size: 50)
                }
                
                ZiuqText(text: p2Name, type: .smallLabel)
                    .fontWeight(.semibold)
                    .lineLimit(1)
            }
        }
        .padding(.leading, 4)
    }
}

#Preview {
    LiveScoreView(p1Image: "https://global.discourse-cdn.com/monzo/original/3X/8/6/866e6d84e8c756b19050fbe2ca0932858118614c.jpg", p1Name: "Player 1", p1Score: 30, p2Image: "https://global.discourse-cdn.com/monzo/original/3X/8/6/866e6d84e8c756b19050fbe2ca0932858118614c.jpg", p2Name: "Player 2", p2Score: 10)
}
