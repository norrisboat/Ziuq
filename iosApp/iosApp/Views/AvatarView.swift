//
//  AvatarView.swift
//  iosApp
//
//  Created by Norris Aboagye on 14/01/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct AvatarView: View {
    let url: URL
    let size: CGFloat

    var body: some View {
        AsyncImage(
            url: url
        ) { phase in
            switch phase {
            case .empty:
                ProgressView()
            case .success(let image):
                image
                    .resizable()
                    .transition(.scale(scale: 0.1, anchor: .center))
            case .failure:
                Image(systemName: "wifi.slash")
            @unknown default:
                EmptyView()
            }
        }
        .size(of: size)
        .clipShape(Circle())
    }
}

#Preview {
    AvatarView(url: "https://global.discourse-cdn.com/monzo/original/3X/8/6/866e6d84e8c756b19050fbe2ca0932858118614c.jpg".toUrl!, size: 56)
}
