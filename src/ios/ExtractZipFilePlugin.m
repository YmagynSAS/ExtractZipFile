//
//  ExtractZipFilePlugin.m
//
//  Created by Shaun Rowe on 10/05/2012.
//  Copyright (c) 2012 Pobl Creative Cyf. All rights reserved.
//

#import "ExtractZipFilePlugin.h"
#import "SSZipArchive.h"

@implementation ExtractZipFilePlugin

@synthesize callbackID;

- (void)extract:(CDVInvokedUrlCommand*)command
{    
    CDVPluginResult* pluginResult = nil;
    
    NSString *file = [command argumentAtIndex:0];
    NSString *destination = [command argumentAtIndex:1];

    if([SSZipArchive unzipFileAtPath:file toDestination:destination delegate:nil]) {
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:destination];
    } else {
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"Could not extract archive"];
    }
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

@end