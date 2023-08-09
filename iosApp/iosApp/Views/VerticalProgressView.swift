//
//  VerticalProgressView.swift
//  iosApp
//
//  Created by Norris Aboagye on 09/08/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct VerticalProgressView: View {
    
    var progress: CGFloat
    var max: CGFloat
    
    var body: some View {
        GeometryReader { proxy in
            ZStack(alignment: .bottom) {
                Rectangle()
                Rectangle()
                    .fill( (progress / max > 0.2) ? Color.deepGreen : Color.red)
                    .frame(maxHeight: proxy.size.height * (progress / max), alignment: .bottom)
            }
        }
        .frame(width: 6)
        .fillHeight()
        .foregroundColor(.gray.opacity(0.3))
    }
}

struct VerticalProgressView_Previews: PreviewProvider {
    static var previews: some View {
        VerticalProgressView(progress: 20, max: 100)
    }
}
